package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder

object RootComponentHolder : BaseComponentHolder<RootApiProvider, RootDependencies>() {

    override fun initializeComponent(dependencies: RootDependencies): RootApiProvider {
        return DaggerRootComponent.factory().create(dependencies)
    }
}