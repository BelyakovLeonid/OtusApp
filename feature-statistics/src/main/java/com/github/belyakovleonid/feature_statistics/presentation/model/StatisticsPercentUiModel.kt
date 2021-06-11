package com.github.belyakovleonid.feature_statistics.presentation.model

import android.graphics.Color
import androidx.annotation.ColorInt
import com.github.belyakovleonid.core.presentation.parseColorOrNull
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory

data class StatisticsPercentUiModel(
    val id: Long,
    val percent: Float,
    @ColorInt val color: Int
)

fun StatisticsCategory.toPercentUi() = StatisticsPercentUiModel(
    id = id,
    percent = percent,
    color = color.parseColorOrNull() ?: Color.GRAY
)
