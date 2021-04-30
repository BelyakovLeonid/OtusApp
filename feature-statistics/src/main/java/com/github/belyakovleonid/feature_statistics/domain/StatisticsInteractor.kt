package com.github.belyakovleonid.feature_statistics.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import javax.inject.Inject

class StatisticsInteractor @Inject constructor(
    private val repository: StatisticsRepository
) {

    suspend fun getStatisticsForPeriod(): Result<List<StatisticsCategory>> {
        return repository.getStatisticsForPeriod()
    }
}