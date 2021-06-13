package com.github.belyakovleonid.feature_weight_track.di

import com.github.belyakovleonid.feature_weight_track.data.WeightTrackRepositoryImpl
import com.github.belyakovleonid.feature_weight_track.domain.WeightTrackRepository
import dagger.Binds
import dagger.Module

@Module
interface WeightTrackModule {

    @Binds
    fun bindsWeightRepository(repository: WeightTrackRepositoryImpl): WeightTrackRepository
}