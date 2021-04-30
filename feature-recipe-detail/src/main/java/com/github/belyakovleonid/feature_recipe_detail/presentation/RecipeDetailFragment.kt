package com.github.belyakovleonid.feature_recipe_detail.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.observeFlow
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_recipe_detail.R
import com.github.belyakovleonid.feature_recipe_detail.databinding.FRecipeDetailBinding
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailApiProvider
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailComponentHolder


class RecipeDetailFragment : Fragment(R.layout.f_recipe_detail) {

    private lateinit var injector: RecipeDetailApiProvider

    private val viewModel: RecipeDetailViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FRecipeDetailBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector = RecipeDetailComponentHolder.getInstance(getDependencies())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFlow(viewModel.items, ::renderState)
    }

    private fun renderState(state: RecipeDetailContract.State) {
        binding.contentGroup.isVisible = state.isContentVisible
        binding.errorText.isVisible = state.isErrorVisible
        binding.loading.isVisible = state.isLoadingVisible

        when (state) {
            is RecipeDetailContract.State.Data -> {
                binding.recipeName.text = state.recipe.name
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true) {
            RecipeDetailComponentHolder.release()
        }
    }

    companion object {
        const val ARG_RECIPE_ID = "arg_recipe_id"
    }
}