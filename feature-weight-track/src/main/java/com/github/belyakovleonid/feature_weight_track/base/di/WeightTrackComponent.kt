package com.github.belyakovleonid.feature_weight_track.base.di

import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    dependencies = [WeightTrackDependencies::class],
    modules = [WeightTrackModule::class]
)
@FragmentScope
interface WeightTrackComponent : WeightTrackApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: WeightTrackDependencies): WeightTrackComponent
    }
}