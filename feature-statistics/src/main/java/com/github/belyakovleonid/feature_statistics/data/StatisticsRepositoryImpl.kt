package com.github.belyakovleonid.feature_statistics.data

import com.github.belyakovleonid.feature_statistics.domain.StatisticsRepository
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(

) : StatisticsRepository {

    override suspend fun getStatisticsForPeriod(): List<StatisticsCategory> { //todo это mock
        return listOf(

        )
    }
}