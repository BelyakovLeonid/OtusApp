package com.github.belyakovleonid.feature_weight_track.root.presentation

import android.os.Bundle
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackApiProvider
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackComponentHolder
import com.github.belyakovleonid.feature_weight_track.databinding.FWeightTrackBinding

class WeightTrackFragment : BaseFragment<WeightTrackContract.State, WeightTrackContract.SideEffect>(
    R.layout.f_weight_track
) {

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

    override fun renderState(state: WeightTrackContract.State): Unit = with(binding) {
        titleGroup.isVisible = state.isGoalVisible
        chartGroup.isVisible = state.isChartVisible
        emptyStub.isVisible = state.isEmptyGoalVisible
        emptyChartStub.isVisible = state.isEmptyChartVisible
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