package com.github.belyakovleonid.feature_weight_track.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder
import com.github.belyakovleonid.module_injector.NoDependencies

object WeightTrackComponentHolder :
    BaseComponentHolder<WeightTrackApiProvider, NoDependencies>() {

    override fun initializeComponent(dependencies: NoDependencies): WeightTrackApiProvider {
        return DaggerWeightTrackComponent.factory().create()
    }
}