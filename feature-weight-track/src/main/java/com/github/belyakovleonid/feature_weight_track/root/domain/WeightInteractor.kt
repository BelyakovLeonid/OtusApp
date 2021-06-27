package com.github.belyakovleonid.feature_weight_track.root.domain

import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeightInteractor @Inject constructor(
    private val repository: WeightTrackRepository
) {

    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return repository.getWeightTrackAsFlow()
    }

    fun getWeightGoal(): WeightGoal? {
        return repository.getWeightGoal()
    }

    fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return repository.getWeightGoalAsFlow()
    }
}