package com.github.belyakovleonid.feature_statistics.domain.model

data class StatisticsCategory(
    val name: String,
    val iconUrl: String,
    val percent: Float,
    val color: String,
    val subcategories: List<StatisticsSubcategory>
)