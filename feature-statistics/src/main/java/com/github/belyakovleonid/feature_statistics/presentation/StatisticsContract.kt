package com.github.belyakovleonid.feature_statistics.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core_ui.expandablelist.ExpandableList
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsItemUiModel
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel

object StatisticsContract {

    data class State(
        val percents: List<StatisticsPercentUiModel> = emptyList(),
        val categories: ExpandableList<StatisticsItemUiModel, StatisticsItemUiModel.Category> = ExpandableList(),
        val isDataAnimated: Boolean = false,
        val hasError: Boolean = false
    ) : IState

    sealed class Event : IEvent {
        data class CategoryClicked(
            val item: StatisticsItemUiModel.Category
        ) : Event()
    }

    sealed class SideEffect : ISideEffect {
        object AnimateData : SideEffect()
    }
}