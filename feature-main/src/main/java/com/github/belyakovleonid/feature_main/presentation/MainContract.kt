package com.github.belyakovleonid.feature_main.presentation

import com.github.belyakovleonid.core.presentation.IEvent

object MainContract {

    sealed class Event : IEvent {
        object OnScreenOpenEvent : Event()
    }
}