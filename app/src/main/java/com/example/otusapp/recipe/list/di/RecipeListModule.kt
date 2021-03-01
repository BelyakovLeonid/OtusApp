package com.example.otusapp.recipe.list.di

import androidx.lifecycle.ViewModel
import com.example.otusapp.base.di.ViewModelKey
import com.example.otusapp.recipe.list.data.RecipeListRepositoryImpl
import com.example.otusapp.recipe.list.data.remote.RecipeListApi
import com.example.otusapp.recipe.list.domain.RecipeListRepository
import com.example.otusapp.recipe.list.navigator.RecipeListNavigatorImpl
import com.example.otusapp.recipe.list.presentation.RecipeListNavigator
import com.example.otusapp.recipe.list.presentation.RecipeListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create

@Module
interface RecipeListModule {

    @Binds
    fun bindsNavigator(navigator: RecipeListNavigatorImpl): RecipeListNavigator

    @Binds
    fun bindsRepository(repository: RecipeListRepositoryImpl): RecipeListRepository

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    fun bindViewModel(viewModel: RecipeListViewModel): ViewModel

    companion object {

        @Provides
        fun providesApi(retrofit: Retrofit): RecipeListApi = retrofit.create()
    }
}