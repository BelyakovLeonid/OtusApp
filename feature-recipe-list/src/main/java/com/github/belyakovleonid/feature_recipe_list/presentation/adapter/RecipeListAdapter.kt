package com.github.belyakovleonid.feature_recipe_list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.feature_recipe_list.databinding.VRecipeItemBinding
import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListContract
import com.github.belyakovleonid.feature_recipe_list.presentation.model.RecipeUi

class RecipesListAdapter(
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

    fun bind(item: RecipeUi, onItemClick: (IEvent) -> Unit) = with(binding) {
        recipeName.text = item.name
        recipeDescription.text = item.subtitle
        root.setOnClickListener {
            onItemClick.invoke(RecipeListContract.Event.OnItemClickEvent(item))
        }
        recipeImage.load(item.subtitle)
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