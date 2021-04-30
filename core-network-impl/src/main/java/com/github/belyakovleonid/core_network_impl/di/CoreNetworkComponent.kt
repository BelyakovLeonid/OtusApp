package com.github.belyakovleonid.core_network_impl.di

import com.github.belyakovleonid.core_network_api.CoreNetworkApiProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreNetworkModule::class])
interface CoreNetworkComponent : CoreNetworkApiProvider