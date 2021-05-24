package com.github.belyakovleonid.feature_weight_track

import com.github.belyakovleonid.feature_weight_track.presentation.WeightTrackFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object WeightTrackScreens {
    fun weightTrackScreen() = FragmentScreen {
        WeightTrackFragment()
    }
}