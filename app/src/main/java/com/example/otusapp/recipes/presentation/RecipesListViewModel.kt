package com.example.otusapp.recipes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.recipes.data.RecipesRepositoryImpl
import com.example.otusapp.recipes.data.remote.RetrofitClient
import com.example.otusapp.recipes.domain.RecipesInteractor
import com.example.otusapp.recipes.presentation.model.RecipeUiModel
import com.example.otusapp.recipes.presentation.model.toUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RecipesListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<RecipeUiModel>>(null)
    val items: LiveData<List<RecipeUiModel>> = _items

    //todo переписать после DI:
    private val recipesApi = RetrofitClient.getClient()
    private val recipesRepository = RecipesRepositoryImpl(recipesApi)
    private val recipesInteractor = RecipesInteractor(recipesRepository)

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.d("com.example.otusapp", "The exception was thrown: $e")
        }) {
            val recipes = recipesInteractor.loadRecipes().map { it.toUi() }
            _items.postValue(recipes)
        }
    }
}