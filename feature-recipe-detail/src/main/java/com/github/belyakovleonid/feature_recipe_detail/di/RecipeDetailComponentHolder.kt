package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder

object RecipeDetailComponentHolder :
    BaseComponentHolder<RecipeDetailApiProvider, RecipeDetailDependencies>() {

    override fun initializeComponent(dependencies: RecipeDetailDependencies): RecipeDetailApiProvider {
        return DaggerRecipeDetailComponent.factory().create(dependencies)
    }
}