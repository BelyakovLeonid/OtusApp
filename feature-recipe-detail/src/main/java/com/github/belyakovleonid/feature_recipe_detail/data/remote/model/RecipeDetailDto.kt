package com.github.belyakovleonid.feature_recipe_detail.data.remote.model

import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailDto(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val imageUrl: String,
)

fun RecipeDetailDto.toDomain(): RecipeDetail {
    return RecipeDetail(
        id = id,
        name = title,
        subtitle = imageUrl
    )
}