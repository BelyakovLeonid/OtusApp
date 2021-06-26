package com.github.belyakovleonid.feature_weight_track.data

import com.github.belyakovleonid.core.presentation.mapElements
import com.github.belyakovleonid.feature_weight_track.data.local.WeightTrackDao
import com.github.belyakovleonid.feature_weight_track.data.local.model.toDomain
import com.github.belyakovleonid.feature_weight_track.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeightTrackRepositoryImpl @Inject constructor(
    private val weightDao: WeightTrackDao
) : WeightTrackRepository {

    override fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return weightDao.getWeightTracksAsFlow().mapElements { it.toDomain() }
    }
}