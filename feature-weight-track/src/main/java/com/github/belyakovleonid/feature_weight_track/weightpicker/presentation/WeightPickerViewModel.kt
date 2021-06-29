package com.github.belyakovleonid.feature_weight_track.weightpicker.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.toFloat
import com.github.belyakovleonid.core.base.fractionalnumber.toFractional
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core.presentation.toDateString
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_weight_track.base.presentation.model.WeightPickerUiModel
import com.github.belyakovleonid.feature_weight_track.root.domain.WeightInteractor
import com.github.belyakovleonid.feature_weight_track.root.domain.model.WeightTrack
import com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.params.WeightPickerParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeightPickerViewModel @AssistedInject constructor(
    private val weightInteractor: WeightInteractor,
    @Assisted private val params: WeightPickerParams
) : BaseViewModel<WeightPickerContract.State, WeightPickerContract.SideEffect>() {

    init {
        viewModelScope.launch {
            loadWeightTrackByDate(params.localDateOfTrack)
        }
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is WeightPickerContract.Event.WeightChanged -> updateWeight(event.newWeight)
            is WeightPickerContract.Event.DateChanged -> updateDate(event.newDate)
            is WeightPickerContract.Event.ApplyClick -> updateWeightTrack()
        }
    }

    private suspend fun loadWeightTrackByDate(date: LocalDate) {
        val weightTrack = weightInteractor.getWeightTrackByDate(date)
        mutableState.value = WeightPickerContract.State(
            date = weightTrack.date,
            dateText = weightTrack.date.toDateString(DATE_FORMAT),
            weightPickerModel = WeightPickerUiModel(
                weight = weightTrack.weight.toFractional(),
                weightLimits = weightInteractor.getPermittedWeightInterval()
            )
        )
    }

    private fun updateWeight(newWeight: FractionalNumber) {
        mutableState.value?.weightPickerModel?.let { currentPickerModel ->
            mutableState.value = mutableState.value?.copy(
                weightPickerModel = currentPickerModel.copy(
                    weight = newWeight
                )
            )
            mutableSideEffect.offer(WeightPickerContract.SideEffect.AnimateWeight)
        }
    }

    private fun updateDate(newDate: LocalDate) {
        mutableState.value = mutableState.value?.copy(
            dateText = newDate.toDateString(DATE_FORMAT),
            date = newDate
        )
    }

    private fun updateWeightTrack() {
        viewModelScope.launch {
            mutableState.value?.let { currentState ->
                weightInteractor.updateWeightTrack(
                    WeightTrack(
                        weight = currentState.weightPickerModel.weight.toFloat(),
                        date = currentState.date,
                    )
                )
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedVMFactory<WeightPickerViewModel, WeightPickerParams>

    companion object {
        private const val DATE_FORMAT = "d MMMM yyyy г." //todo в ресурсы
    }
}