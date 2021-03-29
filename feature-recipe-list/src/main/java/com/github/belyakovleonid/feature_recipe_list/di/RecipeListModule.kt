package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.feature_recipe_list.data.RecipeListRepositoryImpl
import com.github.belyakovleonid.feature_recipe_list.data.remote.RecipeListApi
import com.github.belyakovleonid.feature_recipe_list.domain.RecipeListRepository
import com.github.belyakovleonid.feature_recipe_list.navigator.RecipeListNavigatorImpl
import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListNavigator
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