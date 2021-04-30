package com.github.belyakovleonid.feature_recipe_list.di

import dagger.Component

@Component(
    dependencies = [RecipeListDependencies::class],
    modules = [RecipeListModule::class]
)
interface RecipeListComponent : RecipeListApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: RecipeListDependencies): RecipeListComponent
    }
}