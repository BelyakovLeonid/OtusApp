package com.github.belyakovleonid.feature_statistics.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsItemUiModel
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import com.github.belyakovleonid.feature_statistics.presentation.model.WeightTrackUiModel

object StatisticsContract {

    sealed class State : IState {
        data class Data(
            val weightTrackChart: List<WeightTrackUiModel>,
            val statisticPercents: List<StatisticsPercentUiModel>,
            val statisticCategories: List<StatisticsItemUiModel>
        ) : State()

        object NoElements : State()
        object Error : State()
    }

    sealed class Event : IEvent {
        data class CategoryClicked(
            val item: StatisticsItemUiModel.Category
        ) : Event()

        data class ChartItemClicked(
            val item: WeightTrackUiModel
        ) : Event()
    }
}