package com.github.belyakovleonid.feature_weight_track.weightpicker.presentation.params

import com.github.belyakovleonid.core.viewmodel.BaseParams
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class WeightPickerParams(
    val localDateOfTrack: LocalDate
) : BaseParams
