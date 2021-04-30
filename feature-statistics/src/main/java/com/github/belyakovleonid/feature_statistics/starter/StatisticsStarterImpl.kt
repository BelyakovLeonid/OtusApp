package com.github.belyakovleonid.feature_statistics.starter

import com.github.belyakovleonid.core.starters.StatisticsStarter
import com.github.belyakovleonid.feature_statistics.StatisticsScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class StatisticsStarterImpl @Inject constructor(
    private val router: Router
) : StatisticsStarter {

    override fun start() {
        router.navigateTo(StatisticsScreens.statisticScreen())
    }
}