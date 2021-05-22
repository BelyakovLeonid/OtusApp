package com.github.belyakovleonid.feature_statistics.domain.model

import kotlinx.datetime.LocalDate

data class WeightTrack(
    val weight: Float,
    val date: LocalDate
)