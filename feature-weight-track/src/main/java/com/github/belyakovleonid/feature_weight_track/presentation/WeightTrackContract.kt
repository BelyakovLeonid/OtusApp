package com.github.belyakovleonid.feature_weight_track.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_weight_track.presentation.model.WeightTrackUiModel

object WeightTrackContract {

    sealed class State : IState {
        object NoElements : State()
        object Error : State()
        data class Data(
            val chartData: List<WeightTrackUiModel>,
        ) : State()
    }

    sealed class Event : IEvent {
        data class ChartItemClicked(
            val item: WeightTrackUiModel
        ) : Event()
    }
}