package com.github.belyakovleonid.feature_statistics.data

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.WeightTrackRepository
import com.github.belyakovleonid.feature_statistics.domain.model.WeightTrack
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class WeightTrackRepositoryImpl @Inject constructor() : WeightTrackRepository {

    //todo it is a mock
    override suspend fun getWeightTrackingInfo(): Result<List<WeightTrack>> {
        return Result.Success(
            listOf(
                WeightTrack(73f, LocalDate(2020, 10, 19)),
                WeightTrack(85f, LocalDate(2020, 10, 20)),
                WeightTrack(110f, LocalDate(2020, 11, 21)),
                WeightTrack(90f, LocalDate(2020, 12, 22)),
                WeightTrack(100f, LocalDate(2020, 12, 23))
            )
        )
    }
}