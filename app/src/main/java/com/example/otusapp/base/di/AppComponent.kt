package com.example.otusapp.base.di

import com.github.belyakovleonid.core_network_api.CoreNetworkProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(
    dependencies = [CoreNetworkProvider::class],
    modules = [NavigationModule::class]
)
@Singleton
interface AppComponent {

    fun provideRetrofit(): Retrofit
    fun provideCicerone(): Cicerone<Router>
    fun provideRouter(): Router


    @Component.Factory
    interface Factory {
        fun create(networkProvider: CoreNetworkProvider): AppComponent
    }
}