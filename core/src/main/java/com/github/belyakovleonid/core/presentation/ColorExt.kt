package com.github.belyakovleonid.core.presentation

import android.graphics.Color
import androidx.annotation.ColorInt

@ColorInt
fun String?.parseColorOrNull(): Int? {
    if (isNullOrBlank()) {
        return null
    }
    return try {
        Color.parseColor(trim())
    } catch (e: NumberFormatException) {
        null
    } catch (e: IllegalArgumentException) {
        null
    }
}