package com.example.otusapp.recipe.list.di

import com.example.otusapp.recipe.list.data.RecipeListRepositoryImpl
import com.example.otusapp.recipe.list.data.remote.RecipeListApi
import com.example.otusapp.recipe.list.domain.RecipeListRepository
import com.example.otusapp.recipe.list.navigator.RecipeListNavigatorImpl
import com.example.otusapp.recipe.list.presentation.RecipeListNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [RecipeListBindsModule::class])
object RecipeListModule {


    @Provides
    fun providesApi(retrofit: Retrofit): RecipeListApi = retrofit.create()
}

@Module
interface RecipeListBindsModule {

    @Binds
    fun bindsNavigator(navigator: RecipeListNavigatorImpl): RecipeListNavigator

    @Binds
    fun bindsRepository(repository: RecipeListRepositoryImpl): RecipeListRepository
}