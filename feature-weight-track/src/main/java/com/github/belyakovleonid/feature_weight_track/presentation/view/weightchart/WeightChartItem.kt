package com.github.belyakovleonid.feature_weight_track.presentation.view.weightchart

import android.graphics.PointF
import com.github.belyakovleonid.core_ui.expandablelist.PositionedText
import com.github.belyakovleonid.feature_weight_track.presentation.model.WeightTrackUiModel

data class WeightChartItem(
    val rawItem: WeightTrackUiModel,
    val isSelected: Boolean,
    val coordinates: PointF,
    val label: PositionedText,
)
