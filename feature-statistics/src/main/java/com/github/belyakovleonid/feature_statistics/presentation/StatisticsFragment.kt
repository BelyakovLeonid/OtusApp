package com.github.belyakovleonid.feature_statistics.presentation

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.databinding.FStatisticsBinding
import com.github.belyakovleonid.feature_statistics.di.StatisticsApiProvider
import com.github.belyakovleonid.feature_statistics.di.StatisticsComponentHolder

class StatisticsFragment : BaseFragment<StatisticsContract.State>(R.layout.f_statistics) {

    private lateinit var injector: StatisticsApiProvider

    override val viewModel: StatisticsViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FStatisticsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = StatisticsComponentHolder.getInstance(getDependencies())
    }

    override fun renderState(state: StatisticsContract.State) {
    }
}