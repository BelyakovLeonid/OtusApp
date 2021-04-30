package com.github.belyakovleonid.feature_statistics

import com.github.belyakovleonid.feature_statistics.presentation.StatisticsScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object StatisticsScreens {
    fun statisticScreen() = FragmentScreen {
        StatisticsScreen()
    }
}