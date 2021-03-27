package com.github.belyakovleonid.core_network_impl.di

import com.github.belyakovleonid.core_network_api.CoreNetworkProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreNetworkModule::class])
interface CoreNetworkComponent : CoreNetworkProvider