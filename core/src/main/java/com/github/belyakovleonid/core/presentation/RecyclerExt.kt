package com.github.belyakovleonid.core.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollToEndListener(
    triggerCount: Int,
    onScrollToEnd: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var lastVisibleItemPosition: Int = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val totalItemCount = layoutManager.itemCount
                val newLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition < newLastVisibleItemPosition &&
                    totalItemCount - newLastVisibleItemPosition <= triggerCount
                ) {
                    lastVisibleItemPosition = newLastVisibleItemPosition
                    onScrollToEnd.invoke()
                }
            }
        }
    })
}