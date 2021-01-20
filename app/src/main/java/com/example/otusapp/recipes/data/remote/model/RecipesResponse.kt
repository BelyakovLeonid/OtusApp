package com.example.otusapp.recipes.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("results")
    val results: List<RecipeDto>,
    @SerializedName("totalResults")
    val totalResults: Int,
)