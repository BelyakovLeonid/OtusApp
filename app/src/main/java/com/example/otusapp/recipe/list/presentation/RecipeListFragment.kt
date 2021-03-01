package com.example.otusapp.recipe.list.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.otusapp.OtusApp
import com.example.otusapp.R
import com.example.otusapp.base.utils.observeFlow
import com.example.otusapp.databinding.FRecipesListBinding
import com.example.otusapp.recipe.list.presentation.adapter.RecipesAdapter
import javax.inject.Inject


class RecipeListFragment : Fragment(R.layout.f_recipes_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<RecipeListViewModel> { viewModelFactory }
    private val binding by viewBinding(FRecipesListBinding::bind)

    private val recipesAdapter by lazy {
        RecipesAdapter(viewModel::submitEvent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val recipeListComponent = (requireActivity().application as OtusApp)
            .appComponent
            .recipeListComponent()
            .create()
        recipeListComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        observeFlow(viewModel.items, ::renderState)
    }

    private fun handleView() {
        with(binding.recipesList) {
            adapter = recipesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var lastVisibleItemPosition: Int = 0

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val totalItemCount = layoutManager.itemCount
                        val newLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition < newLastVisibleItemPosition &&
                            totalItemCount - newLastVisibleItemPosition <= 3 // todo нужную константу
                        ) {
                            lastVisibleItemPosition = newLastVisibleItemPosition
                            viewModel.submitEvent(RecipeListContract.Event.OnScrolledToEnd)
                        }
                    }
                }

            })
        }
    }

    private fun renderState(state: RecipeListContract.State) {
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
}