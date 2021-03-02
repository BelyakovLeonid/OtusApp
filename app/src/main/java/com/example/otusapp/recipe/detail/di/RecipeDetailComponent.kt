package com.example.otusapp.recipe.detail.di

import com.example.otusapp.base.di.AppComponent
import com.example.otusapp.base.di.scopes.FragmentScope
import com.example.otusapp.recipe.detail.presentation.RecipeDetailViewModel
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RecipeDetailModule::class]
)
@FragmentScope
interface RecipeDetailComponent {

    val viewModel: RecipeDetailViewModel

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RecipeDetailComponent
    }
}