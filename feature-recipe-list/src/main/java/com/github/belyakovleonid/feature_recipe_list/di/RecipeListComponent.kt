package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    dependencies = [RecipeListDependencies::class],
    modules = [RecipeListModule::class]
)
@FragmentScope
interface RecipeListComponent : RecipeListApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: RecipeListDependencies): RecipeListComponent
    }
}