package com.example.otusapp.root.presentation

import androidx.lifecycle.ViewModel
import com.example.otusapp.base.presentation.IEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val navigator: MainNavigator
) : ViewModel() {

    fun submitEvent(event: IEvent) {
        when (event) {
            is MainContract.Event.OnScreenOpenEvent -> navigator.openRecipesList()
        }
    }
}