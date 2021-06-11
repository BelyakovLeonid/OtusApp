package com.github.belyakovleonid.core.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.belyakovleonid.core.presentation.ISideEffect
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core.presentation.observeFlow

abstract class BaseFragment<S : IState, E : ISideEffect>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {
    protected abstract val viewModel: BaseViewModel<S, E>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeFlow(viewModel.state, ::renderState)
        observeFlow(viewModel.sideEffect, ::reactToSideEffect)
    }

    protected abstract fun renderState(state: S)

    open fun reactToSideEffect(effect: E) = Unit

    open fun setupView() = Unit
}