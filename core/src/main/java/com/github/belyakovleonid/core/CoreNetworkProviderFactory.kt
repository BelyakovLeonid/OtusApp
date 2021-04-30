package com.github.belyakovleonid.core

import com.github.belyakovleonid.core_network_api.CoreNetworkApiProvider
import com.github.belyakovleonid.core_network_impl.di.DaggerCoreNetworkComponent

object CoreNetworkApiFactory {

    fun createNetworkApi(): CoreNetworkApiProvider {
        return DaggerCoreNetworkComponent.create()
    }
}