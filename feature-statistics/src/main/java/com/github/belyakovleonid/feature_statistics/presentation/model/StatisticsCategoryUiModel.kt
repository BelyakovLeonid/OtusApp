package com.github.belyakovleonid.feature_statistics.presentation.model

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

data class StatisticsCategoryUiModel(
    val name: String,
    val iconUrl: String,
    val percentText: String,
    val subcategories: List<StatisticsSubcategoryUiModel>
)

fun StatisticsCategory.toUi() = StatisticsCategoryUiModel(
    name = name,
    iconUrl = iconUrl,
    percentText = percent.toString(),
    subcategories = subcategories.map(StatisticsSubcategory::toUi),
)