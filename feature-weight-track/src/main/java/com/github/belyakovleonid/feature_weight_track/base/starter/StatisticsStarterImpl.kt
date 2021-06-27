package com.github.belyakovleonid.feature_weight_track.base.starter

import com.github.belyakovleonid.core.starters.WeightTrackStarter
import com.github.belyakovleonid.feature_weight_track.WeightTrackScreens
import javax.inject.Inject

class WeightTrackStarterImpl @Inject constructor() : WeightTrackStarter {

    override fun getScreen() = WeightTrackScreens.weightTrackScreen()
}