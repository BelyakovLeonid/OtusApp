package com.github.belyakovleonid.core.presentation

import android.content.res.Resources
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage / 100F
    val dm = Resources.getSystem().displayMetrics
    val percentWidth = dm.widthPixels * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun DialogFragment.setFullScreen() {
    dialog?.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}