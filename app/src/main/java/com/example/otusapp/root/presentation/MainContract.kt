package com.example.otusapp.root.presentation

import com.example.otusapp.base.presentation.IEvent

object MainContract {

    sealed class Event : IEvent {
        object OnScreenOpenEvent : Event()
    }
}