package com.example.otusapp.recipe.detail.di

import androidx.lifecycle.ViewModel
import com.example.otusapp.base.di.ViewModelKey
import com.example.otusapp.recipe.detail.data.RecipeDetailRepositoryImpl
import com.example.otusapp.recipe.detail.data.remote.RecipeDetailApi
import com.example.otusapp.recipe.detail.domain.RecipeDetailRepository
import com.example.otusapp.recipe.detail.presentation.RecipeDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create

@Module
interface RecipeDetailModule {

    @Binds
    fun bindsRepository(repository: RecipeDetailRepositoryImpl): RecipeDetailRepository

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel::class)
    fun bindViewModel(viewModel: RecipeDetailViewModel): ViewModel

    companion object {

        @Provides
        fun provideApi(retrofit: Retrofit): RecipeDetailApi = retrofit.create()
    }
}