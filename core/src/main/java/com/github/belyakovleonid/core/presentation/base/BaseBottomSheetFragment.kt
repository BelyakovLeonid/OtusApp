package com.github.belyakovleonid.core.presentation.base

import android.os.Bundle
import android.view.View
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core.presentation.observeFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<S : IState, E : ISideEffect>(
) : BottomSheetDialogFragment() {

    protected abstract val viewModel: BaseViewModel<S, E>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeFlow(viewModel.state, ::renderState)
        observeFlow(viewModel.sideEffect, ::reactToSideEffect)
    }

    protected fun submitEvent(event: IEvent) {
        viewModel.submitEvent(event)
    }

    protected abstract fun renderState(state: S)

    open fun reactToSideEffect(effect: E) = Unit

    open fun setupView() = Unit
}