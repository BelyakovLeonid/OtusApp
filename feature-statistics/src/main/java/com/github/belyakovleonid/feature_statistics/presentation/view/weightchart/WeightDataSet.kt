package com.github.belyakovleonid.feature_statistics.presentation.view.weightchart

import com.github.belyakovleonid.feature_statistics.presentation.model.WeightTrackUiModel


data class WeightDataSet(
    val rawData: List<WeightTrackUiModel>,
    val maxWeight: Float,
    val minWeight: Float
) {
    val lastIndex: Int get() = rawData.lastIndex

    companion object {
        val EMPTY_SET = WeightDataSet(
            rawData = emptyList(),
            maxWeight = 0f,
            minWeight = 0f
        )
    }
}