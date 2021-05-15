package com.github.belyakovleonid.feature_statistics.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.StatisticsInteractor
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
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
        }
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val result = statisticsInteractor.getStatisticsForPeriod()
            mutableState.value = transmitResultToState(result)
        }
    }

    private fun transmitResultToState(
        result: Result<List<StatisticsCategory>>
    ): StatisticsContract.State {
        return when (result) {
            is Result.Success -> {
                val resultSorted = result.value.sortedByDescending { it.percent }
                val percents = resultSorted.map(StatisticsCategory::toPercentUi)
                val categories = resultSorted.map(StatisticsCategory::toUi)

                if (percents.isNotEmpty() && categories.isNotEmpty()) {
                    StatisticsContract.State.Data(
                        statisticPercents = percents,
                        statisticCategories = categories
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