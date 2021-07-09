package com.github.belyakovleonid.core.presentation

import android.animation.ValueAnimator

inline fun <T> ValueAnimator.addProgressListener(crossinline onProgress: (T) -> Unit) {
    this.addUpdateListener {
        val progress = it.animatedValue as? T ?: return@addUpdateListener
        onProgress.invoke(progress)
    }
}