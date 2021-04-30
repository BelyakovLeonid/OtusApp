package com.github.belyakovleonid.feature_statistics.di

import com.github.belyakovleonid.feature_statistics.data.StatisticsRepositoryImpl
import com.github.belyakovleonid.feature_statistics.domain.StatisticsRepository
import dagger.Binds
import dagger.Module

@Module
interface StatisticsModule {

    @Binds
    fun bindsRepository(repository: StatisticsRepositoryImpl): StatisticsRepository
}