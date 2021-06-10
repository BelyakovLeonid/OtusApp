package com.github.belyakovleonid.core_ui.expandablelist.utils

import android.graphics.Path
import android.graphics.PointF

fun Path.moveTo(point: PointF) {
    moveTo(point.x, point.y)
}

fun Path.lineTo(point: PointF) {
    lineTo(point.x, point.y)
}

fun Path.addCircle(center: PointF, radius: Float, direction: Path.Direction) {
    addCircle(center.x, center.y, radius, direction)
}