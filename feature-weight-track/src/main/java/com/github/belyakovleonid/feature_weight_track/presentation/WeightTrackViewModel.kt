package com.github.belyakovleonid.feature_weight_track.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import javax.inject.Inject

class WeightTrackViewModel @Inject constructor(

) : BaseViewModel<WeightTrackContract.State>() {


    override fun submitEvent(event: IEvent) {
        when (event) {
            is WeightTrackContract.Event.ChartItemClicked -> {
                val currentValue = mutableState.value as? WeightTrackContract.State.Data ?: return
                val newChartData = currentValue.chartData.toMutableList()
                val index = newChartData.indexOf(event.item)
                newChartData[index] = event.item.copy(isSelected = !event.item.isSelected)
                mutableState.value = currentValue.copy(
                    chartData = newChartData
                )
            }
        }
    }
}