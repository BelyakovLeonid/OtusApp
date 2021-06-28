package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.base.BaseDialogFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.setFullScreen
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackApiProvider
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackComponentHolder
import com.github.belyakovleonid.feature_weight_track.databinding.FGoalPickerBinding

class GoalPickerDialogFragment : BaseDialogFragment<GoalPickerContract.State, ISideEffect>(
    R.layout.f_goal_picker
) {

    private lateinit var injector: WeightTrackApiProvider
    override val viewModel: GoalPickerViewModel by viewModel { injector.goalPickerViewModel }
    private val binding by viewBinding(FGoalPickerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = WeightTrackComponentHolder.getInstance(getDependencies())
    }

    override fun setupView() {
        setFullScreen()

        with(binding) {
            weightPicker.onMinusListener = { numberPartSelected, currentWeight ->
                viewModel.submitEvent(
                    GoalPickerContract.Event.ControlClick(
                        isAdd = false,
                        numberPartSelected = numberPartSelected,
                        weight = currentWeight
                    )
                )
            }
            weightPicker.onPlusListener = { numberPartSelected, currentWeight ->
                viewModel.submitEvent(
                    GoalPickerContract.Event.ControlClick(
                        isAdd = true,
                        numberPartSelected = numberPartSelected,
                        weight = currentWeight
                    )
                )
            }
            applyButton.setOnClickListener {
                viewModel.submitEvent(
                    GoalPickerContract.Event.ApplyClick(
                        weight = weightPicker.getCurrentWeight()
                    )
                )
                dismiss()
            }
        }
    }

    override fun renderState(state: GoalPickerContract.State) {
        binding.weightPicker.setWeight(state.weight, state.animated)
    }
}