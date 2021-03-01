package com.example.otusapp.recipe.detail.di

import com.example.otusapp.recipe.detail.data.remote.RecipeDetailApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
object RecipeDetailModule {

    @Provides
    fun provideApi(retrofit: Retrofit): RecipeDetailApi = retrofit.create()
}