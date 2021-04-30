package com.github.belyakovleonid.feature_statistics.data

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.core_network_api.model.convertTo
import com.github.belyakovleonid.feature_statistics.data.remote.StatisticsApi
import com.github.belyakovleonid.feature_statistics.data.remote.model.StatisticsCategoryDto
import com.github.belyakovleonid.feature_statistics.data.remote.model.toDomain
import com.github.belyakovleonid.feature_statistics.domain.StatisticsRepository
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val api: StatisticsApi
) : StatisticsRepository {

    override suspend fun getStatisticsForPeriod(): Result<List<StatisticsCategory>> {
        return api.loadStatistics().convertTo { it.map(StatisticsCategoryDto::toDomain) }
    }
}