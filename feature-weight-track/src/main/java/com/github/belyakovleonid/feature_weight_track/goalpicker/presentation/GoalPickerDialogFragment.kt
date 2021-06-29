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
            weightPicker.onChangeQuantityListener = { newWeight ->
                submitEvent(GoalPickerContract.Event.WeightChanged(newWeight))
            }
            applyButton.setOnClickListener {
                submitEvent(GoalPickerContract.Event.ApplyClick)
                dismiss()
            }
        }
    }

    override fun renderState(state: GoalPickerContract.State) {
        with(binding) {
            weightPicker.setModel(state.weightPickerModel)
        }
    }

    override fun reactToSideEffect(effect: ISideEffect) {
        when (effect) {
            is GoalPickerContract.SideEffect.AnimateWeight -> {
                binding.weightPicker.animateSelectedPart()
            }
        }
    }
}