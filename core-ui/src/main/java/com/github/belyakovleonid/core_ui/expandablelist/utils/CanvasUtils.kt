package com.github.belyakovleonid.core_ui.expandablelist.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.github.belyakovleonid.core_ui.expandablelist.PositionedText

fun Canvas.drawPositionedText(text: PositionedText, paint: Paint) {
    drawText(text.text, text.coordinates.x, text.coordinates.y, paint)
}

fun Canvas.drawCircle(coordinates: PointF, radius: Float, paint: Paint) {
    drawCircle(coordinates.x, coordinates.y, radius, paint)
}