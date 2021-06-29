package com.github.belyakovleonid.feature_weight_track.weightpicker.presentation

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_weight_track.base.presentation.model.WeightPickerUiModel
import java.time.LocalDate

object WeightPickerContract {

    data class State(
        val weightPickerModel: WeightPickerUiModel,
        val dateText: String,
        val date: LocalDate
    ) : IState

    sealed class Event : IEvent {
        object ApplyClick : Event()

        data class WeightChanged(
            val newWeight: FractionalNumber
        ) : WeightPickerContract.Event()

        data class DateChanged(
            val newDate: LocalDate
        ) : WeightPickerContract.Event()
    }

    sealed class SideEffect : ISideEffect {
        object AnimateWeight : SideEffect()
    }
}