package com.github.belyakovleonid.feature_statistics.domain

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import javax.inject.Inject

class StatisticsInteractor @Inject constructor(
    private val repository: StatisticsRepository
) {

    suspend fun getStatisticsForPeriod(): List<StatisticsCategory> {
        return repository.getStatisticsForPeriod()
    }
}