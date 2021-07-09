package com.github.belyakovleonid.feature_weight_track.weightpicker.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseBottomSheetFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.requireParams
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackApiProvider
import com.github.belyakovleonid.feature_weight_track.base.di.WeightTrackComponentHolder
import com.github.belyakovleonid.feature_weight_track.databinding.FWeightPickerBinding
import java.time.LocalDate
import java.util.*

class WeightPickerDialogFragment
    : BaseBottomSheetFragment<WeightPickerContract.State, WeightPickerContract.SideEffect>(),
    View.OnClickListener {

    private lateinit var injector: WeightTrackApiProvider

    private val binding by viewBinding(FWeightPickerBinding::bind)

    override val viewModel: WeightPickerViewModel by viewModel {
        injector.viewModelFactory.create(requireParams())
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector = WeightTrackComponentHolder.getInstance(getDependencies())
        return inflater.inflate(R.layout.f_weight_picker, container, false)
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.datePicker -> openDatePicker()
        R.id.applyButton -> {
            submitEvent(WeightPickerContract.Event.ApplyClick)
            dismiss() //todo cicerone
        }
        else -> throw NotImplementedError()
    }

    override fun setupView() {
        with(binding) {
            weightPicker.onChangeQuantityListener = { newWeight ->
                submitEvent(WeightPickerContract.Event.WeightChanged(newWeight))
            }
            datePicker.setOnClickListener(this@WeightPickerDialogFragment)
            applyButton.setOnClickListener(this@WeightPickerDialogFragment)
        }
    }

    override fun renderState(state: WeightPickerContract.State) {
        with(binding) {
            weightPicker.setModel(state.weightPickerModel)
            datePicker.text = state.dateText
        }
    }

    override fun reactToSideEffect(effect: WeightPickerContract.SideEffect) {
        when (effect) {
            is WeightPickerContract.SideEffect.AnimateWeight -> {
                binding.weightPicker.animateSelectedPart()
            }
        }
    }

    private fun openDatePicker() {
        val date = viewModel.getCurrentDate()
        val dateListener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            submitEvent(WeightPickerContract.Event.DateChanged(LocalDate.of(y, m, d)))
        }

        DatePickerDialog(
            requireContext(),
            R.style.PickerDialogTheme,
            dateListener,
            date.year,
            date.monthValue,
            date.dayOfMonth
        ).apply {
            this.datePicker.maxDate = Calendar.getInstance().timeInMillis
            show()
        }
    }
}