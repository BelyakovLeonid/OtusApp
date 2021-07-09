package com.github.belyakovleonid.core.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.belyakovleonid.core.AppWithDependenciesProvider
import com.github.belyakovleonid.core.viewmodel.BaseParams
import com.github.belyakovleonid.module_injector.BaseDependencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

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

inline fun <reified D : BaseDependencies> Fragment.getDependencies(): D {
    return (requireActivity().application as AppWithDependenciesProvider)
        .dependenciesProvider
        .provideDependency(D::class.java)
}

fun <T> Fragment.observeFlow(flow: Flow<T>, action: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.collect { action(it) }
    }
}

inline fun <reified T : BaseParams> Fragment.requireParams(): T {
    return requireNotNull(arguments?.getParcelable(FRAGMENT_PARAMS_KEY)) {
        "Parameters require not null"
    }
}

inline fun <reified T : BaseParams, F : Fragment> F.withParams(params: T): F {
    return this.apply {
        arguments = Bundle().apply {
            putParcelable(FRAGMENT_PARAMS_KEY, params)
        }
    }
}