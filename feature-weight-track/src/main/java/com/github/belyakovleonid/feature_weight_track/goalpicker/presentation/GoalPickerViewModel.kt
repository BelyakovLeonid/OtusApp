package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightInteractor
import javax.inject.Inject

class GoalPickerViewModel @Inject constructor(
    private val weightInteractor: WeightInteractor
) : BaseViewModel<GoalPickerContract.State, ISideEffect>() {

    init {
        weightInteractor.getWeightGoalAsFlow()
    }
}