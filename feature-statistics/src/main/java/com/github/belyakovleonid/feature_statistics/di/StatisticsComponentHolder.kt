package com.github.belyakovleonid.feature_statistics.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder
import com.github.belyakovleonid.module_injector.NoDependencies

object StatisticsComponentHolder : BaseComponentHolder<StatisticsApiProvider, NoDependencies>() {

    override fun initializeComponent(dependencies: NoDependencies): StatisticsApiProvider {
        return DaggerStatisticsComponent.factory().create()
    }
}