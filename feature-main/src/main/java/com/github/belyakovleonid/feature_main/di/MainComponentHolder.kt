package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder

object MainComponentHolder : BaseComponentHolder<MainApiProvider, MainDependencies>() {

    override fun initializeComponent(dependencies: MainDependencies): MainApiProvider {
        return DaggerMainComponent.factory().create(dependencies)
    }
}