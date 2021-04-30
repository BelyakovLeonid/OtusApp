package com.github.belyakovleonid.feature_statistics.presentation

import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsCategoryUiModel

object StatisticsContract {
    sealed class State : IState {
        data class Data(
            val statistic: List<StatisticsCategoryUiModel>
        ) : State()

        object NoElements : State()
        object Error : State()
    }

}