package com.github.belyakovleonid.core.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core.presentation.observeFlow

abstract class BaseFragment<S : IState>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {
    protected abstract val viewModel: BaseViewModel<S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeFlow(viewModel.state, ::renderState)
    }

    open fun setupView() = Unit

    protected abstract fun renderState(state: S)
}