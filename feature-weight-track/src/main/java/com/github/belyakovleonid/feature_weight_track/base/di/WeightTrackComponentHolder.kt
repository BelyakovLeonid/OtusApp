package com.github.belyakovleonid.feature_weight_track.base.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder

object WeightTrackComponentHolder :
    BaseComponentHolder<WeightTrackApiProvider, WeightTrackDependencies>() {

    override fun initializeComponent(dependencies: WeightTrackDependencies): WeightTrackApiProvider {
        return DaggerWeightTrackComponent.factory().create(dependencies)
    }
}