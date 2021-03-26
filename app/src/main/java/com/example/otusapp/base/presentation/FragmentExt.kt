package com.example.otusapp.base.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.otusapp.OtusApp
import com.example.otusapp.base.di.AppComponent

inline fun <reified VM : ViewModel> Fragment.viewModel(
    crossinline vmProvider: () -> VM
) = viewModels<VM> {
    object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return vmProvider.invoke() as VM
        }
    }
}

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as OtusApp).appComponent