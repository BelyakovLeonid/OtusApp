package com.github.belyakovleonid.core.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.belyakovleonid.core.AppWithDependenciesProvider
import com.github.belyakovleonid.module_injector.BaseDependencies

inline fun <reified VM : ViewModel> AppCompatActivity.viewModel(
    crossinline vmProvider: () -> VM
) = viewModels<VM> {
    object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return vmProvider.invoke() as VM
        }
    }
}

inline fun <reified D : BaseDependencies> AppCompatActivity.getDependencies(): D {
    return (application as AppWithDependenciesProvider)
        .dependenciesProvider
        .provideDependency(D::class.java)
}