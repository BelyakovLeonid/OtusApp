package com.example.otusapp.recipe.detail.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.otusapp.R
import com.example.otusapp.base.utils.observeFlow
import com.example.otusapp.databinding.FRecipeDetailBinding


class RecipeDetailFragment : Fragment(R.layout.f_recipe_detail) {

    private val viewModel by viewModels<RecipeDetailViewModel>()
    private val binding by viewBinding(FRecipeDetailBinding::bind)

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

    companion object {
        const val ARG_RECIPE_ID = "arg_recipe_id"
    }
}