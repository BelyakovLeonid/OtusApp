package com.github.belyakovleonid.feature_recipe_list.data.remote.model

import com.github.belyakovleonid.feature_recipe_list.domain.model.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val imageUrl: String,
)

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        name = title,
        subtitle = imageUrl
    )
}