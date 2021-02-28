package com.example.otusapp.recipes.data.remote.model

import com.example.otusapp.recipes.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
)

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        name = title,
        subtitle = imageUrl
    )
}