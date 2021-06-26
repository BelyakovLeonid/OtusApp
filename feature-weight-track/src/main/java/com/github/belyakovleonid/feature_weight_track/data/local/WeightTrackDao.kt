package com.github.belyakovleonid.feature_weight_track.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.belyakovleonid.feature_weight_track.data.local.model.WeightTrackEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WeightTrackDao {

    @Query("SELECT * FROM ${WeightTrackEntity.WEIGHT_TRACK_TABLE_NAME}")
    fun getWeightTracksAsFlow(): Flow<List<WeightTrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeightTrack(track: WeightTrackEntity)

    @Query("DELETE FROM ${WeightTrackEntity.WEIGHT_TRACK_TABLE_NAME} WHERE date = :date")
    fun deleteWeightTrackByDate(date: LocalDate)
}