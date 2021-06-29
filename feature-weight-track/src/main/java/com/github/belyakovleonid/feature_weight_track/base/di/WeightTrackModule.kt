package com.github.belyakovleonid.feature_weight_track.base.di

import android.content.Context
import androidx.room.Room
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_weight_track.root.data.WeightTrackRepositoryImpl
import com.github.belyakovleonid.feature_weight_track.root.data.local.WeightTrackDatabase
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightTrackRepository
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
    ): WeightTrackDatabase = Room.databaseBuilder(
        context,
        WeightTrackDatabase::class.java,
        "weight_track_database"
    ).build()

    @JvmStatic
    @Provides
    fun provideWeightTrackDao(database: WeightTrackDatabase) = database.getWeightTrackDao()

    @JvmStatic
    @Provides
    fun provideWeightGoalDao(database: WeightTrackDatabase) = database.getWeightGoalDao()
}

@Module
interface WeightTrackBindsModule {

    @Binds
    fun bindsWeightRepository(repository: WeightTrackRepositoryImpl): WeightTrackRepository

    @Binds
    @FragmentScope
    fun bindsVMFactory(
        factory: WeightPickerViewModel.Factory
    ): AssistedVMFactory<WeightPickerViewModel, WeightPickerParams>
}