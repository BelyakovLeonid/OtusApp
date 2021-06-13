package com.github.belyakovleonid.feature_recipe_list.presentation

import android.os.Bundle
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.addOnScrollToEndListener
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_recipe_list.R
import com.github.belyakovleonid.feature_recipe_list.databinding.FRecipesListBinding
import com.github.belyakovleonid.feature_recipe_list.di.RecipeListApiProvider
import com.github.belyakovleonid.feature_recipe_list.di.RecipeListComponentHolder
import com.github.belyakovleonid.feature_recipe_list.presentation.adapter.RecipesListAdapter


class RecipeListFragment : BaseFragment<RecipeListContract.State>(R.layout.f_recipes_list) {

    private lateinit var injector: RecipeListApiProvider

    override val viewModel: RecipeListViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FRecipesListBinding::bind)

    private val recipesAdapter by lazy {
        RecipesListAdapter(viewModel::submitEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = RecipeListComponentHolder.getInstance(getDependencies())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true) {
            RecipeListComponentHolder.release()
        }
    }

    override fun setupView() = with(binding.recipesList) {
        adapter = recipesAdapter
        addOnScrollToEndListener(SCROLL_TO_END_TRIGGER_COUNT) {
            viewModel.submitEvent(RecipeListContract.Event.OnScrolledToEnd)
        }
    }

    override fun renderState(state: RecipeListContract.State) {
        binding.recipesList.isVisible = state.isContentVisible
        binding.errorText.isVisible = state.isErrorVisible
        binding.loading.isVisible = state.isLoadingVisible

        when (state) {
            is RecipeListContract.State.Data -> {
                recipesAdapter.submitList(state.items)
            }
            is RecipeListContract.State.NoElements -> {
                binding.errorText.text = "Нет элементов..."
            }
            is RecipeListContract.State.Error -> {
                binding.errorText.text = "Что-то пошло не так..."
            }
        }
    }

    companion object {
        private const val SCROLL_TO_END_TRIGGER_COUNT = 3
    }
}