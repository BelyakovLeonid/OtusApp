package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.feature_main.presentation.MainActivity
import com.github.belyakovleonid.feature_main.presentation.MainViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface RootApiProvider : BaseApiProvider {

    val viewModel: MainViewModel

    fun inject(activity: MainActivity)
}