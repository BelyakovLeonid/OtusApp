package com.example.otusapp.recipe.list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.otusapp.base.presentation.IEvent
import com.example.otusapp.databinding.VRecipeItemBinding
import com.example.otusapp.recipe.list.presentation.RecipesListContract
import com.example.otusapp.recipe.list.presentation.model.RecipeUi

class RecipesAdapter(
    private val onItemClick: (IEvent) -> Unit
) : ListAdapter<RecipeUi, ItemViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VRecipeItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class ItemViewHolder(
    private val binding: VRecipeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecipeUi, onItemClick: (IEvent) -> Unit) {
        binding.recipeName.text = item.name
        binding.recipeDescription.text = item.subtitle
        binding.root.setOnClickListener {
            onItemClick.invoke(RecipesListContract.Event.OnItemClickEvent(item))
        }
        binding.recipeImage.load(item.subtitle)
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeUi>() {
    override fun areItemsTheSame(oldItem: RecipeUi, newItem: RecipeUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeUi, newItem: RecipeUi): Boolean {
        return oldItem == newItem
    }
}