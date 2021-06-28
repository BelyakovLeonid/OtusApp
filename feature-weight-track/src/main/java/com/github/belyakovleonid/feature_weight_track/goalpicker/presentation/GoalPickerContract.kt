package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.NumberPart
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState

object GoalPickerContract {

    data class State(
        val weight: FractionalNumber,
        val animated: Boolean = false,
    ) : IState


    sealed class Event : IEvent {
        data class ApplyClick(
            val weight: FractionalNumber
        ) : Event()

        data class ControlClick(
            val isAdd: Boolean,
            val numberPartSelected: NumberPart,
            val weight: FractionalNumber
        ) : Event()
    }
}