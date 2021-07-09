package com.github.belyakovleonid.feature_weight_track.base.domain

import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WeightTrackRepository {
    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>>
    suspend fun getWeightTrackByDate(date: LocalDate): WeightTrack?
    suspend fun deleteWeightTrackByDate(date: LocalDate?)
    suspend fun updateWeightTrack(track: WeightTrack)
}