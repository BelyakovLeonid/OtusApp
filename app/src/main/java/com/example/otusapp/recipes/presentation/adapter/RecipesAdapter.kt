package com.example.otusapp.recipes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otusapp.databinding.VRecipeItemBinding
import com.example.otusapp.recipes.presentation.model.RecipeUiModel

class RecipesAdapter : ListAdapter<RecipeUiModel, ItemViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VRecipeItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ItemViewHolder(
    private val binding: VRecipeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecipeUiModel) {
        binding.vRecipeName.text = item.name
        binding.vRecipeDescription.text = item.subtitle
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeUiModel>() {
    override fun areItemsTheSame(oldItem: RecipeUiModel, newItem: RecipeUiModel): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: RecipeUiModel, newItem: RecipeUiModel): Boolean {
        return false
    }
}