package com.github.belyakovleonid.core.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.belyakovleonid.core.AppWithProvidersFacade
import com.github.belyakovleonid.core.ProvidersFacade
import com.github.belyakovleonid.core.viewmodel.BaseParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val FRAGMENT_PARAMS_KEY = "fragment_params_key"

inline fun <reified VM : ViewModel> Fragment.viewModel(
    crossinline vmProvider: () -> VM
) = viewModels<VM> {
    object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return vmProvider.invoke() as VM
        }
    }
}

val Fragment.providersFacade: ProvidersFacade
    get() = (requireActivity().application as AppWithProvidersFacade).providersFacade

fun <T> Fragment.observeFlow(flow: Flow<T>, action: (T) -> Unit) {
    flow.onEach { action(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
}

inline fun <reified T : BaseParams> Fragment.requireParams(): T {
    return requireNotNull(arguments?.getParcelable(FRAGMENT_PARAMS_KEY)) {
        "Parameters require not null"
    }
}

inline fun <reified T : BaseParams> Fragment.withParams(params: T): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            putParcelable(FRAGMENT_PARAMS_KEY, params)
        }
    }
}