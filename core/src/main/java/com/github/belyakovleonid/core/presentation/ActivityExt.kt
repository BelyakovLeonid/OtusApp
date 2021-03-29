package com.github.belyakovleonid.core.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.belyakovleonid.core.AppWithProvidersFacade
import com.github.belyakovleonid.core.ProvidersFacade

inline fun <reified VM : ViewModel> AppCompatActivity.viewModel(
    crossinline vmProvider: () -> VM
) = viewModels<VM> {
    object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return vmProvider.invoke() as VM
        }
    }
}

val AppCompatActivity.providersFacade: ProvidersFacade
    get() = (application as AppWithProvidersFacade).providersFacade