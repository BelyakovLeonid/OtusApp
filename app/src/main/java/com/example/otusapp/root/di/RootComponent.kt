package com.example.otusapp.root.di

import com.example.otusapp.base.di.AppComponent
import com.example.otusapp.base.di.scopes.ActivityScope
import com.example.otusapp.root.presentation.MainActivity
import com.example.otusapp.root.presentation.MainViewModel
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RootModule::class]
)
@ActivityScope
interface RootComponent {

    val viewModel: MainViewModel

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RootComponent
    }
}