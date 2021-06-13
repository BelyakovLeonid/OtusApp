package com.github.belyakovleonid.feature_weight_track.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack

interface WeightTrackRepository {
    suspend fun getWeightTrackInfo(): Result<List<WeightTrack>>
}