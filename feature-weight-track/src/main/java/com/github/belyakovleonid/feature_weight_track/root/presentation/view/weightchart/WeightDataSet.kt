package com.github.belyakovleonid.feature_weight_track.root.presentation.view.weightchart

import com.github.belyakovleonid.feature_weight_track.root.presentation.model.WeightTrackUiModel


data class WeightDataSet(
    val rawData: List<WeightTrackUiModel>,
    val maxWeight: Float,
    val minWeight: Float
) {
    val lastIndex: Int get() = rawData.lastIndex
    val size: Int get() = rawData.size

    val isFlat: Boolean get() = maxWeight == minWeight
    val weightDelta: Float get() = maxWeight - minWeight

    companion object {
        val EMPTY_SET = WeightDataSet(
            rawData = emptyList(),
            maxWeight = 0f,
            minWeight = 0f
        )
    }
}