package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.module_injector.BaseDependencies
import com.github.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class RootDependencies @Inject constructor(
    val navigatorHolder: NavigatorHolder,
    val recipeListStarter: RecipeListStarter
) : BaseDependencies