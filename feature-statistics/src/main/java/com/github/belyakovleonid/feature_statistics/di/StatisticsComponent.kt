package com.github.belyakovleonid.feature_statistics.di

import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    modules = [StatisticsModule::class]
)
@FragmentScope
interface StatisticsComponent : StatisticsApiProvider {

    @Component.Factory
    interface Factory {
        fun create(): StatisticsComponent
    }
}