package com.github.belyakovleonid.feature_weight_track.root.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_weight_track.base.domain.WeightInteractor
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightGoal
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightTrack
import com.github.belyakovleonid.feature_weight_track.root.presentation.model.WeightTrackUiModel
import com.github.belyakovleonid.feature_weight_track.root.presentation.model.toUi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.abs

class WeightTrackViewModel @Inject constructor(
    private val weightInteractor: WeightInteractor
) : BaseViewModel<WeightTrackContract.State, WeightTrackContract.SideEffect>() {

    init {
        subscribeToWeightTracks()
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is WeightTrackContract.Event.ChartItemClicked -> changeItemState(event.item)
            is WeightTrackContract.Event.DeleteSelectedTrack -> deleteSelectedWeightTrack()
        }
    }

    fun getSelectedDate(): LocalDate? {
        return mutableState.value?.chartData?.find { it.isSelected }?.date
    }

    private fun changeItemState(item: WeightTrackUiModel) {
        val currentValue = mutableState.value ?: return
        val newChartData = currentValue.chartData.map {
            val newState = if (it == item) !item.isSelected else false
            it.copy(isSelected = newState)
        }
        val hasSelected = newChartData.any { it.isSelected }
        mutableState.value = currentValue.copy(
            chartData = newChartData,
            isEditFabVisible = hasSelected,
            isAddFabVisible = !hasSelected,
            isFabAnimated = true,
        )
    }

    private fun deleteSelectedWeightTrack() {
        viewModelScope.launch {
            weightInteractor.deleteWeightTrackByDate(getSelectedDate())
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
        val sortedWeightsByDate = weight.sortedBy { it.date }
        val chartData = sortedWeightsByDate.map(WeightTrack::toUi)
        val currentWeight = sortedWeightsByDate.lastOrNull()?.weight ?: 0F

        return WeightTrackContract.State(
            goalWeight = goal?.weight,
            currentWeight = currentWeight,
            remainWeight = if (goal?.weight != null) abs(goal.weight - currentWeight) else null,
            chartData = chartData,
            isGoalVisible = goal != null,
            isEmptyGoalVisible = goal == null,
            isChartVisible = chartData.isNotEmpty(),
            isEmptyChartVisible = goal != null && chartData.isEmpty(),
            isEditFabVisible = false,
            isAddFabVisible = chartData.isNotEmpty(),
        )
    }
}