package com.example.otusapp.recipes.data.remote.model

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