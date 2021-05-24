package com.github.belyakovleonid.feature_weight_track.starter

import com.github.belyakovleonid.core.starters.WeightTrackStarter
import com.github.belyakovleonid.feature_weight_track.WeightTrackScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class WeightTrackStarterImpl @Inject constructor(
    private val router: Router
) : WeightTrackStarter {

    override fun start() {
        router.navigateTo(WeightTrackScreens.weightTrackScreen())
    }
}