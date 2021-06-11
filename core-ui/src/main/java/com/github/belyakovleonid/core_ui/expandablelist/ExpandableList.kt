package com.github.belyakovleonid.core_ui.expandablelist

import android.util.Log

class ExpandableList<I : Item, E : ExpandableItem>(
    private var internalList: List<I> = emptyList()
) {

    @Suppress("UNCHECKED_CAST", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    fun getChangedStateOfItem(itemId: Any): ExpandableList<I, E> {
        val result = runCatching {
            val newList = internalList.toMutableList()
            val index = newList.indexOfFirst { it.id == itemId }
            val item = newList[index] as? E ?: return this

            if (item.isExpanded()) {
                newList[index] = item.changeExpanded() as I
                newList.removeAll(item.subItems)
            } else {
                newList[index] = item.changeExpanded() as I
                newList.addAll(index + 1, item.subItems as List<I>)
            }
            ExpandableList<I, E>(newList)
        }.onFailure { throwable ->
            Log.e("com.github.belyakovleonid", "error in getChangedStateOfItem $throwable")
        }

        return result.getOrDefault(this)
    }

    fun getItems(): List<I> = internalList
}

interface Item {
    val id: Any
}

interface ExpandableItem : Item {
    val subItems: List<NestedItem>
    fun isExpanded(): Boolean
    fun changeExpanded(): Item
}

interface NestedItem : Item