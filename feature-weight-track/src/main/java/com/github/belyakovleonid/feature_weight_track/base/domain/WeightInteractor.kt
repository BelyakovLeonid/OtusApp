package com.github.belyakovleonid.feature_weight_track.base.domain

import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.DEFAULT_WEIGHT_KG
import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.MAX_PERMITTED_WEIGHT
import com.github.belyakovleonid.feature_weight_track.base.constants.WeightTrackConstants.MIN_PERMITTED_WEIGHT
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class WeightInteractor @Inject constructor(
    private val trackRepository: WeightTrackRepository,
    private val goalRepository: WeightGoalRepository,
) {

    fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return trackRepository.getWeightTrackAsFlow()
    }

    suspend fun getWeightTrackByDate(date: LocalDate): WeightTrack {
        return trackRepository.getWeightTrackByDate(date) ?: WeightTrack(DEFAULT_WEIGHT_KG, date)
    }

    suspend fun updateWeightTrack(track: WeightTrack) {
        return trackRepository.updateWeightTrack(track)
    }

    suspend fun getWeightGoal(): WeightGoal {
        return goalRepository.getWeightGoal() ?: WeightGoal(DEFAULT_WEIGHT_KG)
    }

    fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return goalRepository.getWeightGoalAsFlow()
    }

    suspend fun updateGoalWeight(weight: Float) {
        goalRepository.updateWeightGoal(weight)
    }

    suspend fun deleteWeightTrackByDate(date: LocalDate?) {
        trackRepository.deleteWeightTrackByDate(date)
    }

    fun getPermittedWeightInterval(): ClosedFloatingPointRange<Float> {
        return MIN_PERMITTED_WEIGHT..MAX_PERMITTED_WEIGHT
    }
}