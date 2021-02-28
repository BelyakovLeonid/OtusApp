package com.example.otusapp.recipe.list.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.otusapp.R
import com.example.otusapp.base.utils.observeFlow
import com.example.otusapp.databinding.FRecipesListBinding
import com.example.otusapp.recipe.list.presentation.adapter.RecipesAdapter


class RecipesListFragment : Fragment(R.layout.f_recipes_list) {

    private val viewModel by viewModels<RecipesListViewModel>()
    private val binding by viewBinding(FRecipesListBinding::bind)

    private val recipesAdapter by lazy {
        RecipesAdapter(viewModel::submitEvent)
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
                            viewModel.submitEvent(RecipesListContract.Event.OnScrolledToEnd)
                        }
                    }
                }

            })
        }
    }

    private fun renderState(state: RecipesListContract.State) {
        binding.recipesList.isVisible = state.isContentVisible
        binding.errorText.isVisible = state.isErrorVisible
        binding.loading.isVisible = state.isLoadingVisible

        when (state) {
            is RecipesListContract.State.Data -> {
                recipesAdapter.submitList(state.items)
            }
            is RecipesListContract.State.NoElements -> {
                binding.errorText.text = "Нет элементов..."
            }
            is RecipesListContract.State.Error -> {
                binding.errorText.text = "Что-то пошло не так..."
            }
        }
    }
}