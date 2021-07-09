package com.github.belyakovleonid.feature_weight_track.base.di

import android.content.Context
import androidx.room.Room
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_weight_track.base.data.WeightGoalRepositoryImpl
import com.github.belyakovleonid.feature_weight_track.base.data.WeightTrackRepositoryImpl
import com.github.belyakovleonid.feature_weight_track.base.data.local.WeightDatabase
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightGoalRepository
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.WeightPickerViewModel
import com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.params.WeightPickerParams
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
    ): WeightDatabase = Room.databaseBuilder(
        context,
        WeightDatabase::class.java,
        "weight_track_database"
    ).build()

    @JvmStatic
    @Provides
    fun provideWeightTrackDao(database: WeightDatabase) = database.getWeightTrackDao()

    @JvmStatic
    @Provides
    fun provideWeightGoalDao(database: WeightDatabase) = database.getWeightGoalDao()
}

@Module
interface WeightTrackBindsModule {

    @Binds
    fun bindsTrackRepository(impl: WeightTrackRepositoryImpl): WeightTrackRepository

    @Binds
    fun bindsGoalRepository(impl: WeightGoalRepositoryImpl): WeightGoalRepository

    @Binds
    @FragmentScope
    fun bindsVMFactory(
        factory: WeightPickerViewModel.Factory
    ): AssistedVMFactory<WeightPickerViewModel, WeightPickerParams>
}