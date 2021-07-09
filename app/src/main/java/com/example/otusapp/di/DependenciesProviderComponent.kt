package com.example.otusapp.di

import android.content.Context
import com.example.otusapp.di.test.DependenciesModule
import com.github.belyakovleonid.core.DependenciesProvider
import com.github.belyakovleonid.core_network_api.CoreNetworkApiProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [CoreNetworkApiProvider::class],
    modules = [DependenciesModule::class, StartersModule::class, NavigationModule::class]
)
@Singleton
interface DependenciesProviderComponent {

    val dependenciesProvider: DependenciesProvider

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            networkApiProvider: CoreNetworkApiProvider
        ): DependenciesProviderComponent
    }
}