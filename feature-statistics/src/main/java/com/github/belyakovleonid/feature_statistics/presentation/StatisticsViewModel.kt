package com.github.belyakovleonid.feature_statistics.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.StatisticsInteractor
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.WeightTrack
import com.github.belyakovleonid.feature_statistics.presentation.model.toPercentUi
import com.github.belyakovleonid.feature_statistics.presentation.model.toUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : BaseViewModel<StatisticsContract.State>() {

    init {
        loadStatistics()
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is StatisticsContract.Event.CategoryClicked -> {
                val currentValue = mutableState.value as? StatisticsContract.State.Data ?: return
                val newCategories = currentValue.statisticCategories.toMutableList()
                val index = newCategories.indexOf(event.item)
                if (event.item.expanded) {
                    newCategories[index] = event.item.copy(expanded = false)
                    newCategories.removeAll(event.item.subcategories)
                } else {
                    newCategories[index] = event.item.copy(expanded = true)
                    newCategories.addAll(index + 1, event.item.subcategories)
                }
                mutableState.value = currentValue.copy(
                    statisticCategories = newCategories
                )
            }
            is StatisticsContract.Event.ChartItemClicked -> {
                val currentValue = mutableState.value as? StatisticsContract.State.Data ?: return
                val newChartData = currentValue.weightTrackChart.toMutableList()
                val index = newChartData.indexOf(event.item)
                newChartData[index] = event.item.copy(isSelected = !event.item.isSelected)
                mutableState.value = currentValue.copy(
                    weightTrackChart = newChartData
                )
            }
        }
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val statisticResult = statisticsInteractor.getStatisticsInfo()
            val weightResult = statisticsInteractor.getWeightTrackingInfo()
            mutableState.value = transmitResultToState(statisticResult, weightResult)
        }
    }

    private fun transmitResultToState(
        statisticResult: Result<List<StatisticsCategory>>,
        weightResult: Result<List<WeightTrack>>,
    ): StatisticsContract.State {
        return when {
            statisticResult is Result.Success && weightResult is Result.Success -> {
                val resultSorted = statisticResult.value.sortedByDescending { it.percent }
                val percents = resultSorted.map(StatisticsCategory::toPercentUi)
                val categories = resultSorted.map(StatisticsCategory::toUi)

                val chartData = weightResult.value.sortedBy { it.date }
                    .map(WeightTrack::toUi)

                if (percents.isNotEmpty() && categories.isNotEmpty()) {
                    StatisticsContract.State.Data(
                        weightTrackChart = chartData,
                        statisticPercents = percents,
                        statisticCategories = categories,
                    )
                } else {
                    StatisticsContract.State.NoElements
                }
            }
            else -> {
                StatisticsContract.State.Error
            }
        }
    }
}