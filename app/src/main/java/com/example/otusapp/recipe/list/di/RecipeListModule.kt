package com.example.otusapp.recipe.list.di

import com.example.otusapp.recipe.list.data.remote.RecipeListApi
import com.example.otusapp.recipe.list.navigator.RecipeListNavigatorImpl
import com.example.otusapp.recipe.list.presentation.RecipeListNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
interface RecipeListModule {

    @Binds
    fun bindsNavigator(navigator: RecipeListNavigatorImpl): RecipeListNavigator

    companion object {

        @Provides
        fun providesApi(retrofit: Retrofit): RecipeListApi = retrofit.create()
    }
}