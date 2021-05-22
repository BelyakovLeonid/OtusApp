package com.github.belyakovleonid.feature_statistics.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.model.WeightTrack

interface WeightTrackRepository {
    suspend fun getWeightTrackingInfo(): Result<List<WeightTrack>>
}