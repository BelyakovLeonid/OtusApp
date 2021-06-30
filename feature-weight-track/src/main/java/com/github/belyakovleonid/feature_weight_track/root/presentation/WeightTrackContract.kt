package com.github.belyakovleonid.feature_weight_track.root.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_weight_track.root.presentation.model.WeightTrackUiModel

object WeightTrackContract {

    data class State(
        val goalWeight: Float? = null,
        val remainWeight: Float? = null,
        val currentWeight: Float? = null,
        val chartData: List<WeightTrackUiModel> = emptyList(),
        val isGoalVisible: Boolean = false,
        val isChartVisible: Boolean = false,
        val isEmptyGoalVisible: Boolean = false,
        val isEmptyChartVisible: Boolean = false,
        val isEditFabVisible: Boolean = false,
        val isAddFabVisible: Boolean = false,
        val isFabAnimated: Boolean = false,
    ) : IState

    sealed class Event : IEvent {
        object ChooseGoalClick : Event()
        object DeleteSelectedTrack : Event()

        data class ChartItemClicked(
            val item: WeightTrackUiModel
        ) : Event()
    }

    sealed class SideEffect : ISideEffect {
        object AnimateChart : SideEffect()
    }
}