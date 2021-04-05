package com.example.otusapp.di.test

import com.github.belyakovleonid.core.DependenciesProvider
import com.github.belyakovleonid.module_injector.BaseDependencies
import javax.inject.Inject
import javax.inject.Provider

class DependenciesProviderImpl @Inject constructor(
    private val dependenciesMap: MutableMap<Class<out BaseDependencies>, @JvmSuppressWildcards Provider<BaseDependencies>>
) : DependenciesProvider {

    override fun <D : BaseDependencies> provideDependency(dependencyClass: Class<D>): D {
        val dependencyProvider = dependenciesMap[dependencyClass]
            ?: throw IllegalArgumentException("dependency $dependencyClass not found")
        return dependencyProvider.get() as D
    }
}