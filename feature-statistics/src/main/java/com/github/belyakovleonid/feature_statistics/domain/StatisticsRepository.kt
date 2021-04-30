package com.github.belyakovleonid.feature_statistics.domain

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory

interface StatisticsRepository {

    suspend fun getStatisticsForPeriod(): List<StatisticsCategory>
}