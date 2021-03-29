package com.github.belyakovleonid.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.belyakovleonid.core.AppWithProvidersFacade
import com.github.belyakovleonid.core.ProvidersFacade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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