package com.github.belyakovleonid.core.presentation.base

import androidx.lifecycle.ViewModel
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

abstract class BaseViewModel<S : IState>(
    initialState: S? = null
) : ViewModel() {

    protected val mutableState = MutableStateFlow(initialState)
    val state: Flow<S> = mutableState.filterNotNull()

    open fun submitEvent(event: IEvent) = Unit
}