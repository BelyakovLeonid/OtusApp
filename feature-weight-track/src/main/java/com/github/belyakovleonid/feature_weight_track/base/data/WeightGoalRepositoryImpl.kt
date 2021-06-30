package com.github.belyakovleonid.feature_weight_track.base.data

import com.github.belyakovleonid.feature_weight_track.base.data.local.WeightGoalDao
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.WeightGoalEntity
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.toDomain
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightGoalRepository
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightGoal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightGoalRepositoryImpl @Inject constructor(
    private val goalDao: WeightGoalDao
) : WeightGoalRepository {

    override fun getWeightGoalAsFlow(): Flow<WeightGoal?> {
        return goalDao.getWeightGoalAsFlow().map { it?.toDomain() }
    }

    override suspend fun getWeightGoal(): WeightGoal? {
        return goalDao.getWeightGoal()?.toDomain()
    }

    override suspend fun updateWeightGoal(weight: Float) {
        goalDao.insertGoal(WeightGoalEntity(weight = weight))
    }
}