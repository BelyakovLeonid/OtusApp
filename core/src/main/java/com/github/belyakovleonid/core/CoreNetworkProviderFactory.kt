package com.github.belyakovleonid.core

import com.github.belyakovleonid.core_network_api.CoreNetworkProvider
import com.github.belyakovleonid.core_network_impl.di.DaggerCoreNetworkComponent

object CoreNetworkProviderFactory {

    fun createNetworkProvider(): CoreNetworkProvider {
        return DaggerCoreNetworkComponent.create()
    }
}