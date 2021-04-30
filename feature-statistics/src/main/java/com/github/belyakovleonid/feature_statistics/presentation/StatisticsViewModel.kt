package com.github.belyakovleonid.feature_statistics.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.domain.StatisticsInteractor
import com.github.belyakovleonid.feature_statistics.domain.model.StatisticsCategory
import com.github.belyakovleonid.feature_statistics.presentation.model.toUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : BaseViewModel<StatisticsContract.State>() {

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val result = statisticsInteractor.getStatisticsForPeriod()
            mutableState.value = transmitResultToState(result)
        }
    }

    private fun transmitResultToState(
        result: Result<List<StatisticsCategory>>
    ): StatisticsContract.State {
        return when (result) {
            is Result.Success -> {
                val recipes = result.value.map(StatisticsCategory::toUi)

                if (recipes.isNotEmpty()) {
                    StatisticsContract.State.Data(recipes)
                } else {
                    StatisticsContract.State.NoElements
                }
            }
            else -> {
                StatisticsContract.State.Error
            }
        }
    }
}