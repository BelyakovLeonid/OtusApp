package com.github.belyakovleonid.feature_weight_track.root.presentation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.*
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackApiProvider
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackComponentHolder
import com.github.belyakovleonid.feature_weight_track.databinding.EmptyChartLayoutBinding
import com.github.belyakovleonid.feature_weight_track.databinding.EmptyGoalLayoutBinding
import com.github.belyakovleonid.feature_weight_track.databinding.FWeightTrackBinding
import com.github.belyakovleonid.feature_weight_track.goalpicker.presentation.GoalPickerDialogFragment
import com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.WeightPickerDialogFragment
import com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.params.WeightPickerParams
import java.time.LocalDate

class WeightTrackFragment : BaseFragment<WeightTrackContract.State, WeightTrackContract.SideEffect>(
    R.layout.f_weight_track
), View.OnClickListener {

    private lateinit var injector: WeightTrackApiProvider
    override val viewModel: WeightTrackViewModel by viewModel { injector.weightTrackViewModel }

    private val binding by viewBinding(FWeightTrackBinding::bind)
    private val emptyGoalBinding by viewBinding(EmptyGoalLayoutBinding::bind)
    private val emptyChartBinding by viewBinding(EmptyChartLayoutBinding::bind)

    private var fabAnimator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = WeightTrackComponentHolder.getInstance(getDependencies())
    }

    override fun setupView() {
        emptyGoalBinding.chooseGoalButton.setOnClickListener(this)
        emptyChartBinding.emptyChartFabAdd.setOnClickListener(this)

        with(binding) {
            chartView.onItemSelectListener = {
                submitEvent(WeightTrackContract.Event.ChartItemClicked(it))
            }
            fabAdd.setOnClickListener(this@WeightTrackFragment)
            fabEdit.setOnClickListener(this@WeightTrackFragment)
            fabDelete.setOnClickListener(this@WeightTrackFragment)
            goalSubtitle.setOnClickListener(this@WeightTrackFragment)
        }
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.fabAdd -> updateWeightTrack()
        R.id.fabDelete -> submitEvent(WeightTrackContract.Event.DeleteSelectedTrack)
        R.id.fabEdit -> updateWeightTrack(viewModel.getSelectedDate())
        R.id.goalSubtitle -> chooseGoalWeight()
        R.id.chooseGoalButton -> chooseGoalWeight()
        R.id.emptyChartFabAdd -> updateWeightTrack()
        else -> throw NotImplementedError()
    }

    override fun renderState(state: WeightTrackContract.State): Unit = with(binding) {
        goalSubtitle.text = resources.getString(R.string.weight_track_goal, state.goalWeight)
        goalRemain.text = resources.getString(R.string.weight_track_remain, state.remainWeight)
        goalCurrent.text = resources.getString(R.string.weight_track_current, state.currentWeight)
        titleGroup.isVisible = state.isGoalVisible
        chartGroup.isVisible = state.isChartVisible
        chartView.setData(state.chartData)
        emptyGoalBinding.emptyGoalGroup.isVisible = state.isEmptyGoalVisible
        emptyChartBinding.emptyChartGroup.isVisible = state.isEmptyChartVisible
        animateViewVisibilityIfNeed(
            state.isAddFabVisible,
            state.isEditFabVisible,
            state.isFabAnimated
        )
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

    private fun animateViewVisibilityIfNeed(
        addVisible: Boolean,
        editVisible: Boolean,
        isAnimated: Boolean
    ) {
        fabAnimator?.cancel()
        if (isAnimated) {
            val startValue = if (addVisible) 0F else 1F
            val endValue = if (addVisible) 1F else 0F

            fabAnimator = ValueAnimator.ofFloat(startValue, endValue)
                .apply {
                    doOnStart {
                        binding.fabAdd.visibility = View.VISIBLE
                        binding.fabEdit.visibility = View.VISIBLE
                        binding.fabDelete.visibility = View.VISIBLE
                    }
                    doOnEnd {
                        binding.fabAdd.isVisible = addVisible
                        binding.fabEdit.isVisible = editVisible
                        binding.fabDelete.isVisible = editVisible
                        binding.fabAdd.setAlphaAndScale(1F)
                        binding.fabEdit.setAlphaAndScale(1F)
                        binding.fabDelete.setAlphaAndScale(1F)
                    }
                    addProgressListener<Float> { progress ->
                        binding.fabAdd.setAlphaAndScale(progress)
                        binding.fabEdit.setAlphaAndScale(1 - progress)
                        binding.fabDelete.setAlphaAndScale(1 - progress)
                    }
                    duration = BUTTONS_ANIMATION_DURATION
                }
            fabAnimator?.start()
        } else {
            binding.fabAdd.isVisible = addVisible
            binding.fabEdit.isVisible = editVisible
            binding.fabDelete.isVisible = editVisible
        }
    }

    private fun View.setAlphaAndScale(value: Float) {
        this.alpha = value
        this.setScale(value)
    }

    private fun updateWeightTrack(date: LocalDate? = null) {
        //todo вынести в навигацию ч/з cicerone
        WeightPickerDialogFragment().withParams(WeightPickerParams(date ?: getCurrentDate()))
            .show(childFragmentManager, null)
    }

    private fun chooseGoalWeight() {
        submitEvent(WeightTrackContract.Event.ChooseGoalClick)
        //todo вынести в навигацию ч/з cicerone
        GoalPickerDialogFragment().show(childFragmentManager, null)
    }

    companion object {
        private const val BUTTONS_ANIMATION_DURATION = 400L
    }
}