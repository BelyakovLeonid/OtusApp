package com.example.otusapp.recipe.detail.di

import com.example.otusapp.recipe.detail.data.RecipeDetailRepositoryImpl
import com.example.otusapp.recipe.detail.data.remote.RecipeDetailApi
import com.example.otusapp.recipe.detail.domain.RecipeDetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [RecipeDetailBindsModule::class])
object RecipeDetailModule {

    @Provides
    @JvmStatic
    fun provideApi(retrofit: Retrofit): RecipeDetailApi = retrofit.create()
}

@Module
interface RecipeDetailBindsModule {

    @Binds
    fun bindsRepository(repository: RecipeDetailRepositoryImpl): RecipeDetailRepository
}