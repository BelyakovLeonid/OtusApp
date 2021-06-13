package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.core.starters.StatisticsStarter
import com.github.belyakovleonid.core.starters.WeightTrackStarter
import com.github.belyakovleonid.module_injector.BaseDependencies
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainDependencies @Inject constructor(
    val router: Router,
    val navigatorHolder: NavigatorHolder,
    val recipeListStarter: RecipeListStarter,
    val statisticsStarter: StatisticsStarter, //todo delete
    val weightTrackStarter: WeightTrackStarter//todo delete
) : BaseDependencies