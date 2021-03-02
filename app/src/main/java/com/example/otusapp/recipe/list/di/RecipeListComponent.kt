package com.example.otusapp.recipe.list.di

import com.example.otusapp.base.di.AppComponent
import com.example.otusapp.base.di.scopes.FragmentScope
import com.example.otusapp.recipe.list.presentation.RecipeListViewModel
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RecipeListModule::class]
)
@FragmentScope
interface RecipeListComponent {

    val viewModel: RecipeListViewModel

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RecipeListComponent
    }
}