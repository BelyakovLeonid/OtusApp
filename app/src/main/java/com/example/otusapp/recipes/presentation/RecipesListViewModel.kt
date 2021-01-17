package com.example.otusapp.recipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.otusapp.recipes.presentation.model.RecipeUiModel

class RecipesListViewModel : ViewModel() {
    private val _items = MutableLiveData<List<RecipeUiModel>>(null)
    val items: LiveData<List<RecipeUiModel>> = _items

    init {
        //todo load data
    }
}