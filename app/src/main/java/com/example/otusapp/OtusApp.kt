package com.example.otusapp

import android.app.Application
import com.github.belyakovleonid.core.AppWithProvidersFacade
import com.github.belyakovleonid.core.CoreNetworkProviderFactory
import com.github.belyakovleonid.core.ProvidersFacade

class OtusApp : Application(), AppWithProvidersFacade {

    override val providersFacade: ProvidersFacade by lazy {
        DaggerFacadeComponent.factory().create(CoreNetworkProviderFactory.createNetworkProvider())
    }
}