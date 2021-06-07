package com.github.belyakovleonid.feature_statistics.data.remote.model

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

data class StatisticsSubcategoryDto(
    val id: Long,
    val name: String,
    val percent: Float
)

fun StatisticsSubcategoryDto.toDomain() = StatisticsSubcategory(
    id = id,
    name = name,
    percent = percent
)