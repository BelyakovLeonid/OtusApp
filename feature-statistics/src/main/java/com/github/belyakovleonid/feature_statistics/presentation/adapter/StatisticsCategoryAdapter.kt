package com.github.belyakovleonid.feature_statistics.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.feature_statistics.databinding.ItemCategoryBinding
import com.github.belyakovleonid.feature_statistics.databinding.ItemSubcategoryBinding
import com.github.belyakovleonid.feature_statistics.presentation.StatisticsContract
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsItemUiModel

class StatisticsCategoryAdapter(
    private val onItemClick: (IEvent) -> Unit
) : ListAdapter<StatisticsItemUiModel, RecyclerView.ViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CATEGORY_VIEW_TYPE -> {
                CategoryViewHolder(ItemCategoryBinding.inflate(layoutInflater, parent, false))
            }
            else -> {
                SubcategoryViewHolder(ItemSubcategoryBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(getItem(position) as StatisticsItemUiModel.Category, onItemClick)
            }
            is SubcategoryViewHolder -> {
                holder.bind(getItem(position) as StatisticsItemUiModel.Subcategory)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is StatisticsItemUiModel.Category -> CATEGORY_VIEW_TYPE
            is StatisticsItemUiModel.Subcategory -> SUBCATEGORY_VIEW_TYPE
        }
    }

    companion object {
        const val CATEGORY_VIEW_TYPE = 1
        const val SUBCATEGORY_VIEW_TYPE = 2
    }
}

class CategoryViewHolder(
    private val binding: ItemCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StatisticsItemUiModel.Category, onItemClick: (IEvent) -> Unit) = with(binding) {
        itemImage.load(item.iconUrl)
        itemText.text = item.name
        itemPercent.text = item.percentText
        itemCollapseIcon.rotation = if (item.expanded) 0F else 180F
        root.setOnClickListener {
            onItemClick.invoke(StatisticsContract.Event.CategoryClicked(item))
        }
    }
}

class SubcategoryViewHolder(
    private val binding: ItemSubcategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StatisticsItemUiModel.Subcategory) = with(binding) {
        itemText.text = item.name
        itemPercent.text = item.percentText
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<StatisticsItemUiModel>() {
    override fun areItemsTheSame(
        oldItem: StatisticsItemUiModel,
        newItem: StatisticsItemUiModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: StatisticsItemUiModel,
        newItem: StatisticsItemUiModel
    ): Boolean {
        return oldItem == newItem
    }
}