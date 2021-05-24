package com.github.belyakovleonid.feature_statistics.starter

import com.github.belyakovleonid.core.starters.StatisticsStarter
import com.github.belyakovleonid.feature_statistics.StatisticsScreens
import javax.inject.Inject

class StatisticsStarterImpl @Inject constructor() : StatisticsStarter {

    override fun getScreen() = StatisticsScreens.statisticScreen()
}