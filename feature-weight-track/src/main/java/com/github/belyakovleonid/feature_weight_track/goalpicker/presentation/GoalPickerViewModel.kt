package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.base.fractionalnumber.toFloat
import com.github.belyakovleonid.core.base.fractionalnumber.toFractional
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.base.presentation.model.WeightPickerUiModel
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
                weightPickerModel = WeightPickerUiModel(
                    weight = weight.toFractional(),
                    weightLimits = weightInteractor.getPermittedWeightInterval()
                )
            )
        }
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is GoalPickerContract.Event.WeightChanged -> {
                mutableState.value?.weightPickerModel?.let { currentPickerModel ->
                    mutableState.value = mutableState.value?.copy(
                        weightPickerModel = currentPickerModel.copy(
                            weight = event.newWeight
                        ),
                    )
                    mutableSideEffect.offer(GoalPickerContract.SideEffect.AnimateWeight)
                }
            }
            is GoalPickerContract.Event.ApplyClick -> {
                viewModelScope.launch {
                    mutableState.value?.weightPickerModel?.weight?.let { currentWeight ->
                        weightInteractor.updateGoalWeight(
                            currentWeight.toFloat()
                        )
                    }
                }
            }
        }
    }
}