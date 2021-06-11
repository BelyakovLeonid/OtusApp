package com.github.belyakovleonid.feature_weight_track.presentation

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.databinding.FWeightTrackBinding
import com.github.belyakovleonid.feature_weight_track.di.WeightTrackApiProvider
import com.github.belyakovleonid.feature_weight_track.di.WeightTrackComponentHolder

class WeightTrackFragment :
    BaseFragment<WeightTrackContract.State, WeightTrackContract.SideEffect>(R.layout.f_weight_track) {

    private lateinit var injector: WeightTrackApiProvider

    override val viewModel: WeightTrackViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FWeightTrackBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = WeightTrackComponentHolder.getInstance(getDependencies())
    }

    override fun setupView() = with(binding) {
        chartView.onItemSelectListener = {
            viewModel.submitEvent(WeightTrackContract.Event.ChartItemClicked(it))
        }
    }

    override fun renderState(state: WeightTrackContract.State) = with(binding) {
        chartView.setData(state.chartData)
    }

    override fun reactToSideEffect(effect: WeightTrackContract.SideEffect) = with(binding) {
        chartView.animateData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true) {
            WeightTrackComponentHolder.release()
        }
    }
}