package com.github.belyakovleonid.feature_weight_track.di

import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    modules = [WeightTrackModule::class]
)
@FragmentScope
interface WeightTrackComponent : WeightTrackApiProvider {

    @Component.Factory
    interface Factory {
        fun create(): WeightTrackComponent
    }
}