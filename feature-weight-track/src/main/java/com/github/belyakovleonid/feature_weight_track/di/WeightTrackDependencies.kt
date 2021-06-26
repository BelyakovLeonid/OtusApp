package com.github.belyakovleonid.feature_weight_track.di

import android.content.Context
import com.github.belyakovleonid.module_injector.BaseDependencies
import javax.inject.Inject

class WeightTrackDependencies @Inject constructor(
    val context: Context
) : BaseDependencies
