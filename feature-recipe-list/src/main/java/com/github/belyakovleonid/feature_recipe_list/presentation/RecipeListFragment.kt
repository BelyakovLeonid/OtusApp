package com.github.belyakovleonid.feature_recipe_list.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.observeFlow
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_recipe_list.R
import com.github.belyakovleonid.feature_recipe_list.data.RecipeListRepositoryImpl
import com.github.belyakovleonid.feature_recipe_list.databinding.FRecipesListBinding
import com.github.belyakovleonid.feature_recipe_list.di.RecipeListApiProvider
import com.github.belyakovleonid.feature_recipe_list.di.RecipeListComponentHolder
import com.github.belyakovleonid.feature_recipe_list.presentation.adapter.RecipesListAdapter
import javax.inject.Inject


class RecipeListFragment : Fragment(R.layout.f_recipes_list) {

    private lateinit var injector: RecipeListApiProvider

    @Inject
    lateinit var repositoryImpl: RecipeListRepositoryImpl

    private val viewModel: RecipeListViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FRecipesListBinding::bind)

    private val recipesAdapter by lazy {
        RecipesListAdapter(viewModel::submitEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = RecipeListComponentHolder.getInstance(getDependencies())
        injector.inject(this)
        Log.d("MyTag", "repositoryImpl hash = ${repositoryImpl.hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true) {
            RecipeListComponentHolder.release()
        }
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