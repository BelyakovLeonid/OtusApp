package com.github.belyakovleonid.feature_statistics.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.WeightTrack
import javax.inject.Inject

class StatisticsInteractor @Inject constructor(
    private val statisticRepository: StatisticsRepository,
    private val weightRepository: WeightTrackRepository
) {

    suspend fun getStatisticsInfo(): Result<List<StatisticsCategory>> {
        return statisticRepository.getStatisticsInfo()
    }

    suspend fun getWeightTrackingInfo(): Result<List<WeightTrack>> {
        return weightRepository.getWeightTrackingInfo()
    }
}