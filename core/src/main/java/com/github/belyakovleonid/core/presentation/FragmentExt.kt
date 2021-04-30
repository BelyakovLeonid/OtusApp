package com.github.belyakovleonid.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.belyakovleonid.core.AppWithDependenciesProvider
import com.github.belyakovleonid.module_injector.BaseDependencies
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

inline fun <reified D : BaseDependencies> Fragment.getDependencies(): D {
    return (requireActivity().application as AppWithDependenciesProvider)
        .dependenciesProvider
        .provideDependency(D::class.java)
}

fun <T> Fragment.observeFlow(flow: Flow<T>, action: (T) -> Unit) {
    flow.onEach { action(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
}