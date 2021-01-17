package com.example.otusapp.recipes.data.model

import com.example.otusapp.recipes.domain.model.Recipe

data class RecipeDto(
    val id: Long,
    val name: String,
    val subtitle: String
)

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        name = name,
        subtitle = subtitle
    )
}