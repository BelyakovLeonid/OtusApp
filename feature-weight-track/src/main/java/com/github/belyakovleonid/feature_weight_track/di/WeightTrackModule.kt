package com.github.belyakovleonid.feature_weight_track.di

import android.content.Context
import androidx.room.Room
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.feature_weight_track.data.WeightTrackRepositoryImpl
import com.github.belyakovleonid.feature_weight_track.data.local.WeightTrackDatabase
import com.github.belyakovleonid.feature_weight_track.domain.WeightTrackRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [WeightTrackBindsModule::class])
object WeightTrackModule {

    @JvmStatic
    @Provides
    @FragmentScope
    fun provideWeightTrackDatabase(
        context: Context
    ): WeightTrackDatabase = Room.databaseBuilder(
        context,
        WeightTrackDatabase::class.java,
        "weight_track_database"
    ).build()

    @JvmStatic
    @Provides
    fun provideWeightTrackDao(database: WeightTrackDatabase) = database.getWeightTrackDao()
}

@Module
interface WeightTrackBindsModule {

    @Binds
    fun bindsWeightRepository(repository: WeightTrackRepositoryImpl): WeightTrackRepository
}