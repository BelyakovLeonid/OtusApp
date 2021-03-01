package com.example.otusapp.base.di

import com.example.otusapp.recipe.detail.di.RecipeDetailComponent
import com.example.otusapp.recipe.list.di.RecipeListComponent
import com.example.otusapp.root.di.RootComponent
import dagger.Module

@Module(
    subcomponents = [
        RootComponent::class,
        RecipeListComponent::class,
        RecipeDetailComponent::class
    ]
)
interface SubcomponentsModule