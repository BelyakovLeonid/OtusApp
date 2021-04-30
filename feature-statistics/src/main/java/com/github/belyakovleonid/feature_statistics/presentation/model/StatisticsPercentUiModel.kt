package com.github.belyakovleonid.feature_statistics.presentation.model

import androidx.annotation.ColorInt
import com.github.belyakovleonid.core.presentation.parseColorOrNull
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory

data class StatisticsPercentUiModel(
    val percent: Float,
    @ColorInt val color: Int?
)

fun StatisticsCategory.toPercentUi() = StatisticsPercentUiModel(
    percent = percent,
    color = color.parseColorOrNull()
)
