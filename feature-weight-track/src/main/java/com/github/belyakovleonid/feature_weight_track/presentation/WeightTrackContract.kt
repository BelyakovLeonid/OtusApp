package com.github.belyakovleonid.feature_weight_track.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_weight_track.presentation.model.WeightTrackUiModel

object WeightTrackContract {

    data class State(
        val chartData: List<WeightTrackUiModel> = emptyList(),
        val isChartAnimated: Boolean = false,
        val hasError: Boolean = false
    ) : IState

    sealed class Event : IEvent {
        data class ChartItemClicked(
            val item: WeightTrackUiModel
        ) : Event()
    }

    sealed class SideEffect : ISideEffect {
        object AnimateChart : SideEffect()
    }
}