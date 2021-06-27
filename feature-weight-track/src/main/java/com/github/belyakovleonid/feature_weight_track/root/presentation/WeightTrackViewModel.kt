package com.github.belyakovleonid.feature_weight_track.root.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightInteractor
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import com.github.belyakovleonid.feature_weight_track.root.presentation.model.toUi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class WeightTrackViewModel @Inject constructor(
    private val weightInteractor: WeightInteractor
) : BaseViewModel<WeightTrackContract.State, WeightTrackContract.SideEffect>() {

    init {
        subscribeToWeightTracks()
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
                )
            }
        }
    }

    private fun subscribeToWeightTracks() {
        combine(
            weightInteractor.getWeightTrackAsFlow(),
            weightInteractor.getWeightGoalAsFlow()
        ) { weightTrack, weightGoal ->
            mutableState.value = transmitResultToState(weightTrack, weightGoal)
            mutableSideEffect.send(WeightTrackContract.SideEffect.AnimateChart)
        }.launchIn(viewModelScope)
    }

    private fun transmitResultToState(
        weight: List<WeightTrack>,
        goal: WeightGoal?
    ): WeightTrackContract.State {
        val chartData = weight.sortedBy { it.date }
            .map(WeightTrack::toUi)

        return WeightTrackContract.State(
            goalWeight = goal?.weight,
            chartData = chartData,
            isGoalVisible = goal != null,
            isEmptyGoalVisible = goal == null,
            isChartVisible = chartData.isNotEmpty(),
            isEmptyChartVisible = goal != null && chartData.isEmpty(),
            isEditFabVisible = false,
            isAddFabVisible = true,
        )
    }
}