package com.github.belyakovleonid.feature_statistics

import com.github.belyakovleonid.feature_statistics.presentation.StatisticsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object StatisticsScreens {
    fun statisticScreen() = FragmentScreen {
        StatisticsFragment()
    }
}