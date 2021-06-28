package com.github.belyakovleonid.feature_weight_track.root.data

import com.github.belyakovleonid.core.presentation.mapElements
import com.github.belyakovleonid.feature_weight_track.root.data.local.WeightGoalDao
import com.github.belyakovleonid.feature_weight_track.root.data.local.WeightTrackDao
import com.github.belyakovleonid.feature_weight_track.root.data.local.model.WeightGoalEntity
import com.github.belyakovleonid.feature_weight_track.root.data.local.model.toDomain
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
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

    override fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return goalDao.getWeightGoalAsFlow().map { it?.toDomain() }
    }

    override suspend fun getWeightGoal(): WeightGoal? {
        return goalDao.getWeightGoal()?.toDomain()
    }

    override suspend fun updateGoalWeight(weight: Float) {
        goalDao.insertGoal(WeightGoalEntity(weight = weight))
    }
}