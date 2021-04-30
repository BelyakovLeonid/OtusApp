package com.github.belyakovleonid.feature_statistics.presentation

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.databinding.FStatisticsBinding

class StatisticsScreen : BaseFragment<StatisticsContract.State>(R.layout.f_statistics) {

    override val viewModel: StatisticsViewModel by viewModels()

    private val binding by viewBinding(FStatisticsBinding::bind)

    override fun renderState(state: StatisticsContract.State) {
    }
}