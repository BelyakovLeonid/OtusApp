package com.github.belyakovleonid.core_ui.expandablelist.utils

import android.graphics.PointF
import androidx.core.graphics.minus


fun PointF.distanceTo(other: PointF): Float = this.minus(other).length()

fun PointF.distanceTo(otherX: Float, otherY: Float): Float {
    return this.minus(PointF(otherX, otherY)).length()
}