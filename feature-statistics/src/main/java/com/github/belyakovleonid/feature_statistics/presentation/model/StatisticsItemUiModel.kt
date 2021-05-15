package com.github.belyakovleonid.feature_statistics.presentation.model

import com.github.belyakovleonid.core.presentation.toPercentString
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

sealed class StatisticsItemUiModel(
    open val name: String
) {


    data class Category(
        override val name: String,
        val iconUrl: String,
        val percentText: String,
        val subcategories: List<Subcategory>,
        val expanded: Boolean = false
    ) : StatisticsItemUiModel(name)

    data class Subcategory(
        override val name: String,
        val percentText: String
    ) : StatisticsItemUiModel(name)
}

fun StatisticsCategory.toUi() = StatisticsItemUiModel.Category(
    name = name,
    iconUrl = iconUrl,
    percentText = percent.toPercentString(),
    subcategories = subcategories.map(StatisticsSubcategory::toUi),
)


fun StatisticsSubcategory.toUi() = StatisticsItemUiModel.Subcategory(
    name = name,
    percentText = percent.toPercentString()
)