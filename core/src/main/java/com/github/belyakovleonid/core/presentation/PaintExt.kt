package com.github.belyakovleonid.core.presentation

import android.graphics.Paint
import android.graphics.Rect

fun Paint.measureTextHeight(text: String): Int {
    val textBoundsRect = Rect()
    getTextBounds(text, 0, text.lastIndex, textBoundsRect)
    return textBoundsRect.height()
}