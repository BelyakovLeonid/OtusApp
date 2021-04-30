package com.github.belyakovleonid.feature_statistics.data.remote

data class StatisticsCategoryDto(
    val name: String,
    val iconUrl: String,
    val percent: Double,
    val subcategories: List<StatisticsSubcategoryDto>
)