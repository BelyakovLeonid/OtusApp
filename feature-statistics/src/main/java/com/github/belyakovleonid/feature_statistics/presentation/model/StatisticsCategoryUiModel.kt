package com.github.belyakovleonid.feature_statistics.presentation.model

import androidx.annotation.ColorInt
import com.github.belyakovleonid.core.presentation.parseColorOrNull
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

data class StatisticsCategoryUiModel(
    val name: String,
    val iconUrl: String,
    val percent: Double,
    val percentText: String,
    @ColorInt val color: Int?,
    val subcategories: List<StatisticsSubcategoryUiModel>
)

fun StatisticsCategory.toUi() = StatisticsCategoryUiModel(
    name = name,
    iconUrl = iconUrl,
    percent = percent,
    percentText = percent.toString(),
    color = color.parseColorOrNull(),
    subcategories = subcategories.map(StatisticsSubcategory::toUi),
)