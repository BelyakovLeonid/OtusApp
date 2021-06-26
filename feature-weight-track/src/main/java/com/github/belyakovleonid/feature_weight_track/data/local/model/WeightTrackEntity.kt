package com.github.belyakovleonid.feature_weight_track.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.belyakovleonid.feature_weight_track.data.local.model.WeightTrackEntity.Companion.WEIGHT_TRACK_TABLE_NAME
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack
import java.time.LocalDate


@Entity(tableName = WEIGHT_TRACK_TABLE_NAME)
data class WeightTrackEntity(
    @PrimaryKey(autoGenerate = false)
    val date: LocalDate,
    val weight: Float
) {
    companion object {
        const val WEIGHT_TRACK_TABLE_NAME = "weight_track"
    }
}

fun WeightTrackEntity.toDomain(): WeightTrack {
    return WeightTrack(
        date = date,
        weight = weight
    )
}
