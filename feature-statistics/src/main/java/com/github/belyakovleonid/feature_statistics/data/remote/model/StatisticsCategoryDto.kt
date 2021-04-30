package com.github.belyakovleonid.feature_statistics.data.remote.model

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory

data class StatisticsCategoryDto(
    val name: String,
    val iconUrl: String,
    val percent: Float,
    val color: String,
    val subcategories: List<StatisticsSubcategoryDto>
)

fun StatisticsCategoryDto.toDomain() = StatisticsCategory(
    name = name,
    iconUrl = iconUrl,
    percent = percent,
    color = color,
    subcategories = subcategories.map(StatisticsSubcategoryDto::toDomain)
)