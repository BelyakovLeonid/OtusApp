package com.github.belyakovleonid.feature_statistics.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.core_ui.expandablelist.ExpandableList
import com.github.belyakovleonid.feature_statistics.domain.StatisticsInteractor
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.presentation.model.toPercentUi
import com.github.belyakovleonid.feature_statistics.presentation.model.toUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : BaseViewModel<StatisticsContract.State, StatisticsContract.SideEffect>() {

    init {
        loadStatistics()
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is StatisticsContract.Event.ItemClicked -> selectItem(event.item.id)
            is StatisticsContract.Event.CategoryClicked -> selectItem(event.item.id)
        }
    }

    private fun selectItem(itemId: Long) {
        val currentValue = mutableState.value ?: return
        val prevExpanded = currentValue.categories.getCurrentExpanded()
        val newCategories = currentValue.categories.getChangedStateOfItem(itemId)
        mutableState.value = currentValue.copy(
            categories = newCategories,
            isDataAnimated = false
        )
        when (prevExpanded?.id) {
            itemId -> {
                mutableSideEffect.offer(StatisticsContract.SideEffect.AnimCollapseItem(itemId))
            }
            null -> {
                mutableSideEffect.offer(StatisticsContract.SideEffect.AnimExpandItem(itemId))
            }
            else -> {
                mutableSideEffect.offer(StatisticsContract.SideEffect.AnimCollapseItem(prevExpanded.id))
                mutableSideEffect.offer(StatisticsContract.SideEffect.AnimExpandItem(itemId))
            }
        }
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val result = statisticsInteractor.getStatisticsInfo()
            mutableState.value = transmitResultToState(result)
            mutableSideEffect.send(StatisticsContract.SideEffect.AnimateData)
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

                StatisticsContract.State(
                    percents = percents,
                    categories = ExpandableList(categories),
                    isDataAnimated = true
                )
            }
            else -> {
                StatisticsContract.State(
                    hasError = true
                )
            }
        }
    }
}