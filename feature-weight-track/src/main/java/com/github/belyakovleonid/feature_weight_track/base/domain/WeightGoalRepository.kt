package com.github.belyakovleonid.feature_weight_track.base.domain

import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightGoal
import kotlinx.coroutines.flow.Flow

interface WeightGoalRepository {
    fun getWeightGoalAsFlow(): Flow<WeightGoal?>
    suspend fun getWeightGoal(): WeightGoal?
    suspend fun updateWeightGoal(weight: Float)
}