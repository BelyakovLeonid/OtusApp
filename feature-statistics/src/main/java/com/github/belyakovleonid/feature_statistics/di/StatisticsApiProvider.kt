package com.github.belyakovleonid.feature_statistics.di

import com.github.belyakovleonid.feature_statistics.presentation.StatisticsViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface StatisticsApiProvider : BaseApiProvider {

    val viewModel: StatisticsViewModel
}