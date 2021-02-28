package com.example.otusapp.root.presentation

import androidx.lifecycle.ViewModel
import com.example.otusapp.OtusApp
import com.example.otusapp.base.presentation.IEvent
import com.example.otusapp.root.navigator.MainNavigatorImpl

class MainViewModel : ViewModel() {

    private val navigator = MainNavigatorImpl(OtusApp.cicerone.router)

    fun submitEvent(event: IEvent) {
        when (event) {
            is MainContract.Event.OnScreenOpenEvent -> navigator.openRecipesList()
        }
    }
}