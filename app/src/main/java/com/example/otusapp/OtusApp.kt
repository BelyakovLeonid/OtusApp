package com.example.otusapp

import android.app.Application
import com.example.otusapp.di.DaggerDependenciesProviderComponent
import com.github.belyakovleonid.core.AppWithDependenciesProvider
import com.github.belyakovleonid.core.CoreNetworkApiFactory
import com.github.belyakovleonid.core.DependenciesProvider

class OtusApp : Application(), AppWithDependenciesProvider {

    override val dependenciesProvider: DependenciesProvider by lazy {
        DaggerDependenciesProviderComponent.factory()
            .create(CoreNetworkApiFactory.createNetworkApi())
            .dependenciesProvider
    }
}