package com.github.belyakovleonid.core.presentation.base

import androidx.lifecycle.ViewModel
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<S : IState, E : ISideEffect>(
    initialState: S? = null
) : ViewModel() {

    protected val mutableState = MutableStateFlow(initialState)
    val state: Flow<S> = mutableState.filterNotNull()

    protected val mutableSideEffect = Channel<E>(Channel.BUFFERED)
    val sideEffect: Flow<E> = mutableSideEffect.receiveAsFlow()

    open fun submitEvent(event: IEvent) = Unit
}