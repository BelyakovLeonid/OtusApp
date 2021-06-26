package com.github.belyakovleonid.feature_weight_track.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.belyakovleonid.feature_weight_track.data.local.WeightTrackDatabase.Companion.VERSION
import com.github.belyakovleonid.feature_weight_track.data.local.converters.LocalDateConverter
import com.github.belyakovleonid.feature_weight_track.data.local.model.WeightTrackEntity

@Database(
    entities = [WeightTrackEntity::class],
    version = VERSION,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class WeightTrackDatabase : RoomDatabase() {

    abstract fun getWeightTrackDao(): WeightTrackDao

    companion object {
        const val VERSION = 1
    }
}