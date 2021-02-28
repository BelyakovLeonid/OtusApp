package com.example.otusapp.recipe.list.presentation.model

import com.example.otusapp.recipe.list.domain.model.Recipe

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
