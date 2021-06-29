package com.github.belyakovleonid.feature_weight_track.base.presentation.model

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber

data class WeightPickerUiModel(
    val weight: FractionalNumber,
    val weightLimits: ClosedFloatingPointRange<Float>
)