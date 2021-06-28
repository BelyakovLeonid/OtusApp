package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.NumberPart
import com.github.belyakovleonid.core.base.fractionalnumber.toFloat
import com.github.belyakovleonid.core.base.fractionalnumber.toFractional
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoalPickerViewModel @Inject constructor(
    private val weightInteractor: WeightInteractor
) : BaseViewModel<GoalPickerContract.State, ISideEffect>() {

    init {
        viewModelScope.launch {
            val weight = weightInteractor.getWeightGoal().weight
            mutableState.value = GoalPickerContract.State(
                weight = weight.toFractional(),
                animated = false
            )
        }
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is GoalPickerContract.Event.ControlClick -> {
                val newWeight = event.weight + getWeightDelta(event.isAdd, event.numberPartSelected)
                val weight = if (weightInteractor.isWeightPermitted(newWeight)) {
                    newWeight
                } else {
                    event.weight
                }

                mutableState.value = mutableState.value?.copy(
                    weight = weight,
                    animated = true
                )
            }
            is GoalPickerContract.Event.ApplyClick -> {
                viewModelScope.launch {
                    weightInteractor.updateGoalWeight(event.weight.toFloat())
                }
            }
        }
    }

    private fun getWeightDelta(isAdd: Boolean, numberPart: NumberPart): FractionalNumber {
        val multiplier = if (isAdd) POSITIVE_MULTIPLIER else NEGATIVE_MULTIPLIER
        val delta = if (numberPart == NumberPart.INT_PART) WEIGHT_INT_STEP else WEIGHT_FRACT_STEP
        return (multiplier * delta).toFractional()
    }

    companion object {
        private const val POSITIVE_MULTIPLIER = 1F
        private const val NEGATIVE_MULTIPLIER = -1F
        private const val WEIGHT_INT_STEP = 1F
        private const val WEIGHT_FRACT_STEP = 0.1F
    }
}