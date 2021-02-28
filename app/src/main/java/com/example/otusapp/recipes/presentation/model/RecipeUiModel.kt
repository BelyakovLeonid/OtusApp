package com.example.otusapp.recipes.presentation.model

import com.example.otusapp.recipes.domain.model.Recipe

data class RecipeUiModel(
    val id: Long,
    val name: String,
    val subtitle: String
)


fun Recipe.toUi(): RecipeUiModel {
    return RecipeUiModel(
        id = id,
        name = name,
        subtitle = subtitle
    )
}
