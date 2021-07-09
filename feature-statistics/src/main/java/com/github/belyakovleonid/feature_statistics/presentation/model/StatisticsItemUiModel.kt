package com.github.belyakovleonid.feature_statistics.presentation.model

import com.github.belyakovleonid.core.presentation.toPercentString
import com.github.belyakovleonid.core_ui.expandablelist.ExpandableItem
import com.github.belyakovleonid.core_ui.expandablelist.Item
import com.github.belyakovleonid.core_ui.expandablelist.NestedItem
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsSubcategory

sealed class StatisticsItemUiModel(
    override val id: Long,
    open val name: String
) : Item {

    data class Category(
        override val id: Long,
        override val name: String,
        val iconUrl: String,
        val percentText: String,
        override val subItems: List<Subcategory>,
        val expanded: Boolean = false
    ) : StatisticsItemUiModel(id, name), ExpandableItem {

        override fun isExpanded() = expanded
        override fun changeExpanded() = copy(expanded = !expanded)
    }

    data class Subcategory(
        override val id: Long,
        override val name: String,
        val percentText: String
    ) : StatisticsItemUiModel(id, name), NestedItem
}

fun StatisticsCategory.toUi() = StatisticsItemUiModel.Category(
    id = id,
    name = name,
    iconUrl = iconUrl,
    percentText = percent.toPercentString(),
    subItems = subcategories.map(StatisticsSubcategory::toUi),
)

fun StatisticsSubcategory.toUi() = StatisticsItemUiModel.Subcategory(
    id = id,
    name = name,
    percentText = percent.toPercentString()
)