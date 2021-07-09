package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_weight_track.base.presentation.model.WeightPickerUiModel

object GoalPickerContract {

    data class State(
        val weightPickerModel: WeightPickerUiModel
    ) : IState


    sealed class Event : IEvent {
        object ApplyClick : Event()

        data class WeightChanged(
            val newWeight: FractionalNumber
        ) : Event()
    }

    sealed class SideEffect : ISideEffect {
        object AnimateWeight : SideEffect()
    }
}