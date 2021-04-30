package com.github.belyakovleonid.feature_statistics.data.remote

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.data.remote.model.StatisticsCategoryDto

interface StatisticsApi {
    suspend fun loadStatistics(): Result<List<StatisticsCategoryDto>>
}