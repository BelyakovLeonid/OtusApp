package com.github.belyakovleonid.feature_weight_track.root.domain

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.toFloat
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

    suspend fun getWeightGoal(): WeightGoal {
        return repository.getWeightGoal() ?: WeightGoal(DEFAULT_WEIGHT_KG)
    }

    fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return repository.getWeightGoalAsFlow()
    }

    fun isWeightPermitted(newWeight: FractionalNumber): Boolean {
        return newWeight.toFloat() in PERMITTED_WEIGHT_DIAPASON
    }

    suspend fun updateGoalWeight(weight: Float) {
        repository.updateGoalWeight(weight)
    }

    companion object {
        private val PERMITTED_WEIGHT_DIAPASON = 30F..150F
        private const val DEFAULT_WEIGHT_KG = 60F
    }
}