package com.github.belyakovleonid.feature_recipe_detail.presentation.model

import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail

data class RecipeDetailUi(
    val id: Long,
    val name: String,
    val subtitle: String
)

fun RecipeDetail.toUi(): RecipeDetailUi {
    return RecipeDetailUi(
        id = id,
        name = name,
        subtitle = subtitle
    )
}
