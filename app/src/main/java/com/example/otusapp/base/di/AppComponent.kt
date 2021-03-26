package com.example.otusapp.base.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        NavigationModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun provideRetrofit(): Retrofit
    fun provideCicerone(): Cicerone<Router>
    fun provideRouter(): Router
}