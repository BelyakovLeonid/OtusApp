package com.github.belyakovleonid.feature_weight_track.di

import com.github.belyakovleonid.feature_weight_track.presentation.WeightTrackViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface WeightTrackApiProvider : BaseApiProvider {
    val viewModel: WeightTrackViewModel
}