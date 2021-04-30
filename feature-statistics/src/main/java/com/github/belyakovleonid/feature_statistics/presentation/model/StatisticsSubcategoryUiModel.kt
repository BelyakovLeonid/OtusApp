package com.github.belyakovleonid.feature_statistics.presentation.model

import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

data class StatisticsSubcategoryUiModel(
    val name: String,
    val percentText: String
)

fun StatisticsSubcategory.toUi() = StatisticsSubcategoryUiModel(
    name = name,
    percentText = percent.toString(), // todo
)