package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.core.di.ActivityScope
import dagger.Component

@Component(
    dependencies = [RootDependencies::class],
)
@ActivityScope
interface RootComponent : RootApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: RootDependencies): RootComponent
    }
}