package com.github.belyakovleonid.feature_statistics.data.remote.model

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

data class StatisticsSubcategoryDto(
    val name: String,
    val percent: Double
)

fun StatisticsSubcategoryDto.toDomain() = StatisticsSubcategory(
    name = name,
    percent = percent
)