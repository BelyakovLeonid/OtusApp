package com.github.belyakovleonid.feature_statistics.presentation.model

import com.github.belyakovleonid.feature_statistics.domain.model.WeightTrack
import kotlinx.datetime.LocalDate

data class WeightTrackUiModel(
    val isSelected: Boolean,
    val weight: Float,
    val date: LocalDate
)

fun WeightTrack.toUi() = WeightTrackUiModel(
    isSelected = false,
    weight = weight,
    date = date
)
