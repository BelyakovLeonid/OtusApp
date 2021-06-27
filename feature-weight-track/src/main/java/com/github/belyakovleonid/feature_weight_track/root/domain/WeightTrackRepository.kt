package com.github.belyakovleonid.feature_weight_track.root.domain

import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow

interface WeightTrackRepository {
    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>>
    fun getWeightGoalAsFlow(): Flow<WeightGoal?>
    fun getWeightGoal(): WeightGoal?
}