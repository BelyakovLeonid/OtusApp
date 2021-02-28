package com.example.otusapp.base.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Fragment.observeFlow(flow: Flow<T>, action: (T) -> Unit) {
    flow.onEach { action(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
}