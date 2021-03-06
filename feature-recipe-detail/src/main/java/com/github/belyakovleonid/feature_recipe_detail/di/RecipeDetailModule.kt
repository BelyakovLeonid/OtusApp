package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_recipe_detail.data.RecipeDetailRepositoryImpl
import com.github.belyakovleonid.feature_recipe_detail.data.remote.RecipeDetailApi
import com.github.belyakovleonid.feature_recipe_detail.domain.RecipeDetailRepository
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailParams
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailViewModel
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

    @Binds
    @FragmentScope
    fun bindsVMFactory(
        factory: RecipeDetailViewModel.Factory
    ): AssistedVMFactory<RecipeDetailViewModel, RecipeDetailParams>
}