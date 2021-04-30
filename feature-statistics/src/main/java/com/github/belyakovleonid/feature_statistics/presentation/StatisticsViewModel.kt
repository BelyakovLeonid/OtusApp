package com.github.belyakovleonid.feature_statistics.presentation

import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.feature_statistics.domain.StatisticsInteractor
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : BaseViewModel<StatisticsContract.State>() {

}