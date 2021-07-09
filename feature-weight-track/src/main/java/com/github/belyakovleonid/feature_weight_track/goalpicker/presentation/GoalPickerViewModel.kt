package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.toFloat
import com.github.belyakovleonid.core.base.fractionalnumber.toFractional
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightInteractor
import com.github.belyakovleonid.feature_weight_track.base.presentation.model.WeightPickerUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoalPickerViewModel @Inject constructor(
    private val weightInteractor: WeightInteractor
) : BaseViewModel<GoalPickerContract.State, ISideEffect>() {

    init {
        viewModelScope.launch {
            loadGoalWeight()
        }
    }

    private suspend fun loadGoalWeight() {
        val weight = weightInteractor.getWeightGoal().weight
        mutableState.value = GoalPickerContract.State(
            weightPickerModel = WeightPickerUiModel(
                weight = weight.toFractional(),
                weightLimits = weightInteractor.getPermittedWeightInterval()
            )
        )
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is GoalPickerContract.Event.WeightChanged -> updateWeight(event.newWeight)
            is GoalPickerContract.Event.ApplyClick -> updateGoal()
        }
    }

    private fun updateWeight(newWeight: FractionalNumber) {
        mutableState.value?.weightPickerModel?.let { currentPickerModel ->
            mutableState.value = mutableState.value?.copy(
                weightPickerModel = currentPickerModel.copy(
                    weight = newWeight
                ),
            )
            mutableSideEffect.offer(GoalPickerContract.SideEffect.AnimateWeight)
        }
    }

    private fun updateGoal() {
        viewModelScope.launch {
            mutableState.value?.weightPickerModel?.weight?.let { currentWeight ->
                weightInteractor.updateGoalWeight(currentWeight.toFloat())
            }
        }
    }
}