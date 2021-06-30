package com.github.belyakovleonid.feature_weight_track.root.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.belyakovleonid.feature_weight_track.root.data.local.WeightTrackDatabase.Companion.VERSION
import com.github.belyakovleonid.feature_weight_track.root.data.local.converters.LocalDateConverter
import com.github.belyakovleonid.feature_weight_track.root.data.local.model.WeightGoalEntity
import com.github.belyakovleonid.feature_weight_track.root.data.local.model.WeightTrackEntity

@Database(
    entities = [
        WeightTrackEntity::class,
        WeightGoalEntity::class
    ],
    version = VERSION,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class WeightTrackDatabase : RoomDatabase() {

    abstract fun getWeightTrackDao(): WeightTrackDao
    abstract fun getWeightGoalDao(): WeightGoalDao

    companion object {
        const val VERSION = 1
    }
}