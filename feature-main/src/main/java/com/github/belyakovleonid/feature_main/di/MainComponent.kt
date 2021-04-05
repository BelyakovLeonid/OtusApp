package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.core.di.ActivityScope
import dagger.Component

@Component(
    dependencies = [MainDependencies::class],
)
@ActivityScope
interface MainComponent : MainApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: MainDependencies): MainComponent
    }
}