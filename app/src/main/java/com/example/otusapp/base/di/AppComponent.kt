package com.example.otusapp.base.di

import com.example.otusapp.recipe.detail.di.RecipeDetailComponent
import com.example.otusapp.recipe.list.di.RecipeListComponent
import com.example.otusapp.root.di.RootComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        SubcomponentsModule::class,
        NetworkModule::class,
        NavigationModule::class
    ]
)
@Singleton
interface AppComponent {

    fun rootComponent(): RootComponent.Factory
    fun recipeListComponent(): RecipeListComponent.Factory
    fun recipeDetailComponent(): RecipeDetailComponent.Factory
}