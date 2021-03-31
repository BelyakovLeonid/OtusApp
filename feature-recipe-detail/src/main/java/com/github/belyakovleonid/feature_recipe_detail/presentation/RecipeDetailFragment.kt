package com.github.belyakovleonid.feature_recipe_detail.presentation

import android.content.Context
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.providersFacade
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_recipe_detail.R
import com.github.belyakovleonid.feature_recipe_detail.databinding.FRecipeDetailBinding
import com.github.belyakovleonid.feature_recipe_detail.di.DaggerRecipeDetailComponent
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailComponent


class RecipeDetailFragment : BaseFragment<RecipeDetailContract.State>(R.layout.f_recipe_detail) {

    private lateinit var injector: RecipeDetailComponent

    override val viewModel: RecipeDetailViewModel by viewModel {
        injector.viewModelFactory.create(
            RecipeDetailParams(arguments?.getLong(ARG_RECIPE_ID))
        )
    }

    private val binding by viewBinding(FRecipeDetailBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector = DaggerRecipeDetailComponent.factory().create(providersFacade)
    }

    override fun renderState(state: RecipeDetailContract.State) {
        binding.contentGroup.isVisible = state.isContentVisible
        binding.errorText.isVisible = state.isErrorVisible
        binding.loading.isVisible = state.isLoadingVisible

        when (state) {
            is RecipeDetailContract.State.Data -> {
                binding.recipeName.text = state.recipe.name
            }
        }
    }

    companion object {
        const val ARG_RECIPE_ID = "arg_recipe_id"
    }
}