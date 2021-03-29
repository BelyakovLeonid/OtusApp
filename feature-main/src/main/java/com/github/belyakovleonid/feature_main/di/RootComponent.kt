package com.github.belyakovleonid.feature_main.di

import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.di.ActivityScope
import com.github.belyakovleonid.feature_main.presentation.MainActivity
import com.github.belyakovleonid.feature_main.presentation.MainViewModel
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