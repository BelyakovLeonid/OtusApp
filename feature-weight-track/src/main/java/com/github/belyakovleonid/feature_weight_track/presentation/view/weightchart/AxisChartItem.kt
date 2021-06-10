package com.github.belyakovleonid.feature_weight_track.presentation.view.weightchart

import android.graphics.PointF
import com.github.belyakovleonid.core_ui.expandablelist.PositionedText

data class AxisChartItem(
    val text: PositionedText,
    val start: PointF = PointF(),
    val end: PointF = PointF()
)