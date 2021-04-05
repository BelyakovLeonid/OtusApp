package com.github.belyakovleonid.core_network_api

import retrofit2.Retrofit

interface CoreNetworkApiProvider {
    fun provideRetrofit(): Retrofit
}