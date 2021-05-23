package com.github.belyakovleonid.feature_statistics.presentation.view.weightchart

data class AxisChartItem(
    val text: String,
    val textX: Float,
    val textY: Float,
    val startX: Float = 0f,
    val startY: Float = 0f,
    val endX: Float = 0f,
    val endY: Float = 0f
)