package com.github.belyakovleonid.feature_weight_track.base.di

import com.github.belyakovleonid.feature_weight_track.goalpicker.presentation.GoalPickerViewModel
import com.github.belyakovleonid.feature_weight_track.root.presentation.WeightTrackViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface WeightTrackApiProvider : BaseApiProvider {
    val weightTrackViewModel: WeightTrackViewModel
    val goalPickerViewModel: GoalPickerViewModel
}