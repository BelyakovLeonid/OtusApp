package com.github.belyakovleonid.feature_weight_track.root.domain.model

import java.time.LocalDate


data class WeightTrack(
    val weight: Float,
    val date: LocalDate
)