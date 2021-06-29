package com.github.belyakovleonid.feature_weight_track.root.domain

import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.DEFAULT_WEIGHT_KG
import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.MAX_PERMITTED_WEIGHT
import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.MIN_PERMITTED_WEIGHT
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class WeightInteractor @Inject constructor(
    private val repository: WeightTrackRepository
) {

    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return repository.getWeightTrackAsFlow()
    }

    suspend fun getWeightTrackByDate(date: LocalDate): WeightTrack {
        return repository.getWeightTrackByDate(date) ?: WeightTrack(DEFAULT_WEIGHT_KG, date)
    }

    suspend fun updateWeightTrack(track: WeightTrack) {
        return repository.updateWeightTrack(track)
    }

    suspend fun getWeightGoal(): WeightGoal {
        return repository.getWeightGoal() ?: WeightGoal(DEFAULT_WEIGHT_KG)
    }

    fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return repository.getWeightGoalAsFlow()
    }

    suspend fun updateGoalWeight(weight: Float) {
        repository.updateGoalWeight(weight)
    }

    fun getPermittedWeightInterval(): ClosedFloatingPointRange<Float> {
        return MIN_PERMITTED_WEIGHT..MAX_PERMITTED_WEIGHT
    }
}