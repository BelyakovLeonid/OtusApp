package com.github.belyakovleonid.core

import com.github.belyakovleonid.module_injector.BaseDependencies

interface DependenciesProvider {
    fun <D : BaseDependencies> provideDependency(dependencyClass: Class<D>): D
}