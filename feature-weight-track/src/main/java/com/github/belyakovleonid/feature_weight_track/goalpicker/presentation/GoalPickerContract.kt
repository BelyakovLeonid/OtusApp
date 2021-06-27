package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState

object GoalPickerContract {

    data class State(
        val goalWeight: Float? = null,
    ) : IState


    sealed class Event : IEvent {
        data class WeightGoalPicked(
            val goalWeight: Float?
        ) : Event()
    }
}