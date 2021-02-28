package com.example.otusapp.recipe.list.data.remote.response

import com.example.otusapp.recipe.list.data.remote.model.RecipeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    @SerialName("offset")
    val offset: Int,
    @SerialName("number")
    val number: Int,
    @SerialName("results")
    val results: List<RecipeDto>,
    @SerialName("totalResults")
    val totalResults: Int,
)