package com.github.belyakovleonid.core_ui.expandablelist

import android.util.Log

class ExpandableList<I : Item, E : ExpandableItem>(
    private var internalList: List<I> = emptyList()
) {

    fun getItemById(itemId: Any): I? {
        return internalList.firstOrNull { it.id == itemId }
    }

    @Suppress("UNCHECKED_CAST")
    fun getCurrentExpanded(): E? {
        return internalList.firstOrNull { it is ExpandableItem && it.isExpanded() } as? E
    }

    @Suppress("UNCHECKED_CAST", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    fun getChangedStateOfItem(itemId: Any): ExpandableList<I, E> {
        val result = runCatching {
            val newList = internalList.toMutableList()

            val indexOfExpanded = newList.indexOfFirst { it is ExpandableItem && it.isExpanded() }
            val expandedItem = newList.getOrNull(indexOfExpanded) as? E
            if (expandedItem != null && expandedItem.id != itemId) {
                newList[indexOfExpanded] = expandedItem.changeExpanded() as I
                newList.removeAll(expandedItem.subItems)
            }

            val index = newList.indexOfFirst { it.id == itemId }
            val item = newList.getOrNull(index) as? E ?: return this
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