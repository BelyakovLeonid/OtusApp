package com.github.belyakovleonid.feature_recipe_detail.presentation

import android.content.Context
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.providersFacade
import com.github.belyakovleonid.core.presentation.requireParams
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_recipe_detail.R
import com.github.belyakovleonid.feature_recipe_detail.databinding.FRecipeDetailBinding
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailApiProvider
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailComponentHolder


class RecipeDetailFragment : BaseFragment<RecipeDetailContract.State>(R.layout.f_recipe_detail) {

    private lateinit var injector: RecipeDetailApiProvider

    override val viewModel: RecipeDetailViewModel by viewModel {
        injector.viewModelFactory.create(requireParams())
    }

    private val binding by viewBinding(FRecipeDetailBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector = RecipeDetailComponentHolder.getInstance(getDependencies())
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
}