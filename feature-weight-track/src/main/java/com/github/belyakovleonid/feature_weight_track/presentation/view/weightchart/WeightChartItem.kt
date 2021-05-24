package com.github.belyakovleonid.feature_weight_track.presentation.view.weightchart

import com.github.belyakovleonid.feature_weight_track.presentation.model.WeightTrackUiModel

data class WeightChartItem(
    val rawItem: WeightTrackUiModel,
    val isSelected: Boolean,
    val x: Float,
    val y: Float,
    val labelText: String,
    val labelX: Float,
    val labelY: Float,
)