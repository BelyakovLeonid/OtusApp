package com.example.otusapp

import android.app.Application
import com.example.otusapp.base.di.AppComponent
import com.example.otusapp.base.di.DaggerAppComponent
import com.github.belyakovleonid.core_network.CoreNetworkProviderFactory

class OtusApp : Application() {

    val appComponent: AppComponent by lazy {
        val networkProvider = CoreNetworkProviderFactory.createNetworkProvider()
        DaggerAppComponent.factory().create(networkProvider)
    }
}