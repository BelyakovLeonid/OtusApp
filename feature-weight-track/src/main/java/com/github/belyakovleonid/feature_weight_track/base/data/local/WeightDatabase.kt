package com.github.belyakovleonid.feature_weight_track.base.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.belyakovleonid.feature_weight_track.base.data.local.WeightDatabase.Companion.VERSION
import com.github.belyakovleonid.feature_weight_track.base.data.local.converters.LocalDateConverter
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.WeightGoalEntity
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.WeightTrackEntity

@Database(
    entities = [
        WeightTrackEntity::class,
        WeightGoalEntity::class
    ],
    version = VERSION,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class WeightDatabase : RoomDatabase() {

    abstract fun getWeightTrackDao(): WeightTrackDao
    abstract fun getWeightGoalDao(): WeightGoalDao

    companion object {
        const val VERSION = 1
    }
}