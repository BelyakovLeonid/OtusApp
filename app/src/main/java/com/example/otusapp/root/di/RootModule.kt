package com.example.otusapp.root.di

import androidx.lifecycle.ViewModel
import com.example.otusapp.base.di.ViewModelKey
import com.example.otusapp.root.navigator.MainNavigatorImpl
import com.example.otusapp.root.presentation.MainNavigator
import com.example.otusapp.root.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RootModule {

    @Binds
    fun bindsNavigator(navigator: MainNavigatorImpl): MainNavigator

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(viewModel: MainViewModel): ViewModel
}