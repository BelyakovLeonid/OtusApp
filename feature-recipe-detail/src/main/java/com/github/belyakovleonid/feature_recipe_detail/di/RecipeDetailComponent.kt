package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    dependencies = [RecipeDetailDependencies::class],
    modules = [RecipeDetailModule::class]
)
@FragmentScope
interface RecipeDetailComponent : RecipeDetailApiProvider {

    @Component.Factory
    interface Factory {
        fun create(dependencies: RecipeDetailDependencies): RecipeDetailComponent
    }
}