package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListViewModel
import dagger.Component

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [RecipeListModule::class]
)
@FragmentScope
interface RecipeListComponent {

    val viewModel: RecipeListViewModel

    @Component.Factory
    interface Factory {
        fun create(facade: ProvidersFacade): RecipeListComponent
    }
}