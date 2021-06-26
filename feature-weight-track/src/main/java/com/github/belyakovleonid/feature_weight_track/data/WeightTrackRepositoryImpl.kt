package com.github.belyakovleonid.feature_weight_track.data

import com.github.belyakovleonid.core.presentation.mapElements
import com.github.belyakovleonid.feature_weight_track.data.local.WeightGoalDao
import com.github.belyakovleonid.feature_weight_track.data.local.WeightTrackDao
import com.github.belyakovleonid.feature_weight_track.data.local.model.toDomain
import com.github.belyakovleonid.feature_weight_track.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightTrackRepositoryImpl @Inject constructor(
    private val weightDao: WeightTrackDao,
    private val goalDao: WeightGoalDao
) : WeightTrackRepository {

    override fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return weightDao.getWeightTracksAsFlow().mapElements { it.toDomain() }
    }

    override fun getWeightGoalAsFlow(): Flow<WeightGoal> {
        return goalDao.getWeightGoalAsFlow().map { it.toDomain() }
    }
}