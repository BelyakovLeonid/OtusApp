package com.example.otusapp.base.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.otusapp.OtusApp
import com.example.otusapp.base.di.AppComponent

inline fun <reified VM : ViewModel> AppCompatActivity.viewModel(
    crossinline vmProvider: () -> VM
) = viewModels<VM> {
    object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return vmProvider.invoke() as VM
        }
    }
}

val AppCompatActivity.appComponent: AppComponent
    get() = (application as OtusApp).appComponent