package com.github.belyakovleonid.feature_recipe_list.presentation.model

import com.github.belyakovleonid.feature_recipe_list.domain.model.Recipe

data class RecipeUi(
    val id: Long,
    val name: String,
    val subtitle: String
)


fun Recipe.toUi(): RecipeUi {
    return RecipeUi(
        id = id,
        name = name,
        subtitle = subtitle
    )
}
