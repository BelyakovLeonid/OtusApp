package com.github.belyakovleonid.feature_weight_track.base.data

import com.github.belyakovleonid.core.presentation.mapElements
import com.github.belyakovleonid.feature_weight_track.base.data.local.WeightTrackDao
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.toDomain
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.toEntity
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightTrack
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class WeightTrackRepositoryImpl @Inject constructor(
    private val weightDao: WeightTrackDao,
) : WeightTrackRepository {

    override fun getWeightTrackAsFlow(): Flow<List<WeightTrack>> {
        return weightDao.getWeightTracksAsFlow().mapElements { it.toDomain() }
    }

    override suspend fun getWeightTrackByDate(date: LocalDate): WeightTrack? {
        return weightDao.getWeightTrackByDate(date)?.toDomain()
    }

    override suspend fun updateWeightTrack(track: WeightTrack) {
        weightDao.updateWeightTrack(track.toEntity())
    }

    override suspend fun deleteWeightTrackByDate(date: LocalDate?) {
        weightDao.deleteWeightTrackByDate(date)
    }
}