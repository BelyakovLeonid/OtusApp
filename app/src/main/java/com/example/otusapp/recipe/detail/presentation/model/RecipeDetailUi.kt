package com.example.otusapp.recipe.detail.presentation.model

import com.example.otusapp.recipe.detail.domain.model.RecipeDetail

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
