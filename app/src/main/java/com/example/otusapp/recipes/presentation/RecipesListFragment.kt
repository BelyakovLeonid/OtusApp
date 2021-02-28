package com.example.otusapp.recipes.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.otusapp.R
import com.example.otusapp.databinding.FRecipesListBinding
import com.example.otusapp.recipes.presentation.adapter.RecipesAdapter

class RecipesListFragment : Fragment(R.layout.f_recipes_list) {

    private val viewModel by viewModels<RecipesListViewModel>()
    private val binding by viewBinding(FRecipesListBinding::bind)

    private val adapter = RecipesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        observeViewModel()
    }

    private fun handleView() {
        binding.vItemsList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}