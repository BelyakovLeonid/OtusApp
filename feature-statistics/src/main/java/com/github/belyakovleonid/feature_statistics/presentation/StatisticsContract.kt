package com.github.belyakovleonid.feature_statistics.presentation

import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsCategoryUiModel
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel

object StatisticsContract {
    sealed class State : IState {
        data class Data(
            val statisticPercents: List<StatisticsPercentUiModel>,
            val statisticCategories: List<StatisticsCategoryUiModel>
        ) : State()

        object NoElements : State()
        object Error : State()
    }

}