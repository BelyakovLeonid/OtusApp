package com.github.belyakovleonid.feature_statistics.di

import com.github.belyakovleonid.feature_statistics.data.StatisticsRepositoryImpl
import com.github.belyakovleonid.feature_statistics.data.WeightTrackRepositoryImpl
import com.github.belyakovleonid.feature_statistics.data.remote.StatisticsApi
import com.github.belyakovleonid.feature_statistics.data.remote.StatisticsApiImpl
import com.github.belyakovleonid.feature_statistics.domain.StatisticsRepository
import com.github.belyakovleonid.feature_statistics.domain.WeightTrackRepository
import dagger.Binds
import dagger.Module

@Module
interface StatisticsModule {

    @Binds
    fun bindsApi(api: StatisticsApiImpl): StatisticsApi

    @Binds
    fun bindsRepository(repository: StatisticsRepositoryImpl): StatisticsRepository

    @Binds
    fun bindsWeightRepository(repository: WeightTrackRepositoryImpl): WeightTrackRepository
}