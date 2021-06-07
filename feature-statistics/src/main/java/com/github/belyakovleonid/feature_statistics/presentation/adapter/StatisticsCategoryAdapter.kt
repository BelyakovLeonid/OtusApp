package com.github.belyakovleonid.feature_statistics.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.belyakovleonid.feature_statistics.databinding.ItemCategoryBinding
import com.github.belyakovleonid.feature_statistics.databinding.ItemSubcategoryBinding
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsItemUiModel

class StatisticsCategoryAdapter(
    private val onCategoryClick: (StatisticsItemUiModel.Category) -> Unit
) : ListAdapter<StatisticsItemUiModel, RecyclerView.ViewHolder>(RecipeDiffCallback()) {

    init {
        setHasStableIds(true)
    }

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
                holder.bind(
                    getItem(position) as StatisticsItemUiModel.Category,
                    onCategoryClick,

                    )
            }
            is SubcategoryViewHolder -> {
                holder.bind(getItem(position) as StatisticsItemUiModel.Subcategory)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(
                    getItem(position) as StatisticsItemUiModel.Category,
                    onCategoryClick,
                    payloads.firstOrNull() as? Boolean
                )
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

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    companion object {
        const val CATEGORY_VIEW_TYPE = 1
        const val SUBCATEGORY_VIEW_TYPE = 2
    }
}

class CategoryViewHolder(
    private val binding: ItemCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: StatisticsItemUiModel.Category,
        onItemClick: (StatisticsItemUiModel.Category) -> Unit,
        payloads: Boolean? = null
    ) = with(binding) {
        if (payloads != null) {
            itemCollapseIcon.rotation = if (payloads) 180F else 0F
        } else {
            Log.d("MyTag", "load")
            itemImage.load(item.iconUrl)
            itemText.text = item.name
            itemPercent.text = item.percentText
            itemCollapseIcon.rotation = if (item.expanded) 180F else 0F
        }
        root.setOnClickListener { onItemClick.invoke(item) }
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
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: StatisticsItemUiModel,
        newItem: StatisticsItemUiModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: StatisticsItemUiModel,
        newItem: StatisticsItemUiModel
    ): Any? {
        return if (oldItem is StatisticsItemUiModel.Category &&
            newItem is StatisticsItemUiModel.Category &&
            oldItem.id == newItem.id &&
            oldItem != newItem
        ) {
            newItem.expanded
        } else {
            super.getChangePayload(oldItem, newItem)
        }
    }
}