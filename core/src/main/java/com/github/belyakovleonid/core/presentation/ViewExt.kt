package com.github.belyakovleonid.core.presentation

import android.view.View
import android.view.ViewGroup

fun View.setScale(scale: Float) {
    scaleY = scale
    scaleX = scale
}

fun View.updateSize(w: Int, h: Int? = null) {
    this.layoutParams.width = w
    this.layoutParams.height = h ?: w
}

val View.marginLayoutParams: ViewGroup.MarginLayoutParams?
    get() = layoutParams as? ViewGroup.MarginLayoutParams