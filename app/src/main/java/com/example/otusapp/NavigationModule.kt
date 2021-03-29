package com.example.otusapp

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NavigationModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @JvmStatic
    fun provideNavigatorHolder(
        cicerone: Cicerone<Router>
    ): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @JvmStatic
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}