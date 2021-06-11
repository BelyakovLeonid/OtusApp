package com.github.belyakovleonid.feature_weight_track.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_weight_track.domain.WeightTrackInteractor
import com.github.belyakovleonid.feature_weight_track.domain.model.WeightTrack
import com.github.belyakovleonid.feature_weight_track.presentation.model.toUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeightTrackViewModel @Inject constructor(
    private val weightTrackInteractor: WeightTrackInteractor
) : BaseViewModel<WeightTrackContract.State, WeightTrackContract.SideEffect>() {

    init {
        loadWeightTrack()
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is WeightTrackContract.Event.ChartItemClicked -> {
                val currentValue = mutableState.value ?: return
                val newChartData = currentValue.chartData.map {
                    val newState = if (it == event.item) !event.item.isSelected else false
                    it.copy(isSelected = newState)
                }
                mutableState.value = currentValue.copy(
                    chartData = newChartData,
                    isChartAnimated = false
                )
            }
        }
    }

    private fun loadWeightTrack() {
        viewModelScope.launch {
            val result = weightTrackInteractor.getWeightTrackInfo()
            mutableState.value = transmitResultToState(result)
            mutableSideEffect.send(WeightTrackContract.SideEffect.AnimateChart)
        }
    }

    private fun transmitResultToState(
        result: Result<List<WeightTrack>>
    ): WeightTrackContract.State {
        return when (result) {
            is Result.Success -> {
                val chartData = result.value.sortedBy { it.date }
                    .map(WeightTrack::toUi)

                WeightTrackContract.State(
                    chartData = chartData,
                    isChartAnimated = true
                )
            }
            else -> {
                WeightTrackContract.State(
                    hasError = true
                )
            }
        }
    }
}