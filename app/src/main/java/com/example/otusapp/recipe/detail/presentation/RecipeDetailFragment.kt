package com.example.otusapp.recipe.detail.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.otusapp.R
import com.example.otusapp.base.presentation.appComponent
import com.example.otusapp.base.presentation.viewModel
import com.example.otusapp.base.utils.observeFlow
import com.example.otusapp.databinding.FRecipeDetailBinding
import com.example.otusapp.recipe.detail.di.DaggerRecipeDetailComponent
import com.example.otusapp.recipe.detail.di.RecipeDetailComponent


class RecipeDetailFragment : Fragment(R.layout.f_recipe_detail) {

    private lateinit var injector: RecipeDetailComponent

    private val viewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FRecipeDetailBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector = DaggerRecipeDetailComponent.factory().create(appComponent)
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

    companion object {
        const val ARG_RECIPE_ID = "arg_recipe_id"
    }
}