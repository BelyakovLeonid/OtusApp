package com.example.otusapp.root.di

import com.example.otusapp.root.presentation.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [RootModule::class])
interface RootComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RootComponent
    }

    fun inject(activity: MainActivity)
}