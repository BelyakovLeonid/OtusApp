package com.example.otusapp.recipes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.otusapp.databinding.FRecipesListBinding
import com.example.otusapp.recipes.presentation.adapter.RecipesAdapter

class RecipesListFragment : Fragment() {

    private val viewModel by viewModels<RecipesListViewModel>()

    private var _binding: FRecipesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = RecipesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FRecipesListBinding.inflate(inflater, container, false)
        handleView()
        observeViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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