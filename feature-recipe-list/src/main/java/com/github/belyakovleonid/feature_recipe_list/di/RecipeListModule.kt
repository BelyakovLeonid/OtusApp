package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.feature_recipe_list.data.RecipeListRepositoryImpl
import com.github.belyakovleonid.feature_recipe_list.data.remote.RecipeListApi
import com.github.belyakovleonid.feature_recipe_list.domain.RecipeListRepository
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
    fun bindsRepository(repository: RecipeListRepositoryImpl): RecipeListRepository
}