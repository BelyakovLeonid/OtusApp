package com.github.belyakovleonid.feature_weight_track.domain

import com.github.belyakovleonid.feature_weight_track.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeightTrackInteractor @Inject constructor(
    private val repository: WeightTrackRepository
) {

    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return repository.getWeightTrackAsFlow()
    }

    fun getWeightGoalAsFlow(): Flow<WeightGoal> {
        return repository.getWeightGoalAsFlow()
    }
}