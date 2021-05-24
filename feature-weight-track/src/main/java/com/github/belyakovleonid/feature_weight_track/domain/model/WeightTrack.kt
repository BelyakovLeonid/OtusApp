package com.github.belyakovleonid.feature_weight_track.domain.model

import kotlinx.datetime.LocalDate

data class WeightTrack(
    val weight: Float,
    val date: LocalDate
)