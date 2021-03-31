package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailParams
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailViewModel
import dagger.Component

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [RecipeDetailModule::class]
)
@FragmentScope
interface RecipeDetailComponent {

    val viewModelFactory: AssistedVMFactory<RecipeDetailViewModel, RecipeDetailParams>

    @Component.Factory
    interface Factory {
        fun create(facade: ProvidersFacade): RecipeDetailComponent
    }
}