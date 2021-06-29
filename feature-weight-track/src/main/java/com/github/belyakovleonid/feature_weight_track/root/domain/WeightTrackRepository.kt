package com.github.belyakovleonid.feature_weight_track.root.domain

import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

//todo разбить на WeightTrackRepository && WeightGoalRepository
interface WeightTrackRepository {
    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>>
    suspend fun getWeightTrackByDate(date: LocalDate): WeightTrack?
    suspend fun updateWeightTrack(track: WeightTrack)

    fun getWeightGoalAsFlow(): Flow<WeightGoal?>
    suspend fun getWeightGoal(): WeightGoal?
    suspend fun updateGoalWeight(weight: Float)
}