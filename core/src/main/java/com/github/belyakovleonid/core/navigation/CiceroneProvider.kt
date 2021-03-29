package com.github.belyakovleonid.core.navigation

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

interface CiceroneProvider {

    fun provideNavigatorHolder(): NavigatorHolder

    fun provideRouter(): Router
}