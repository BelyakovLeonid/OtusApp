package com.example.otusapp.base.di

import com.github.terrakok.cicerone.Cicerone
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
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}