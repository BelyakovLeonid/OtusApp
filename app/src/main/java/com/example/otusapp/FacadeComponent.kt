package com.example.otusapp

import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core_network_api.CoreNetworkProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [CoreNetworkProvider::class],
    modules = [StartersModule::class, NavigationModule::class]
)
@Singleton
interface FacadeComponent : ProvidersFacade {


    @Component.Factory
    interface Factory {
        fun create(networkProvider: CoreNetworkProvider): FacadeComponent
    }
}