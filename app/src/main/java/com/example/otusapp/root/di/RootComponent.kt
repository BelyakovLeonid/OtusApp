package com.example.otusapp.root.di

import com.example.otusapp.root.presentation.MainActivity
import com.example.otusapp.root.presentation.MainViewModel
import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.di.ActivityScope
import dagger.Component

@Component(
    dependencies = [ProvidersFacade::class],
)
@ActivityScope
interface RootComponent {

    val viewModel: MainViewModel

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(facade: ProvidersFacade): RootComponent
    }
}