package com.example.otusapp.recipe.detail.di

import com.example.otusapp.recipe.detail.presentation.RecipeDetailViewModel
import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.di.FragmentScope
import dagger.Component

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [RecipeDetailModule::class]
)
@FragmentScope
interface RecipeDetailComponent {

    val viewModel: RecipeDetailViewModel

    @Component.Factory
    interface Factory {
        fun create(facade: ProvidersFacade): RecipeDetailComponent
    }
}