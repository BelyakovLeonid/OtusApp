package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.module_injector.BaseComponentHolder

object RecipeListComponentHolder :
    BaseComponentHolder<RecipeListApiProvider, RecipeListDependencies>() {

    override fun initializeComponent(dependencies: RecipeListDependencies): RecipeListApiProvider {
        return DaggerRecipeListComponent.factory().create(dependencies)
    }
}