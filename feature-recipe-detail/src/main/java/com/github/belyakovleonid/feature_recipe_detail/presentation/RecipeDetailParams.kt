package com.github.belyakovleonid.feature_recipe_detail.presentation

import com.github.belyakovleonid.core.viewmodel.BaseParams
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeDetailParams(
    val recipeId: Long?
) : BaseParams