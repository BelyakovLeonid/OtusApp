package com.example.otusapp.root.di

import com.example.otusapp.root.navigator.MainNavigatorImpl
import com.example.otusapp.root.presentation.MainNavigator
import dagger.Binds
import dagger.Module

@Module
interface RootModule {

    @Binds
    fun bindsNavigator(navigator: MainNavigatorImpl): MainNavigator
}