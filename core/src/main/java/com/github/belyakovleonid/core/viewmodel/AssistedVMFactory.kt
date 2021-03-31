package com.github.belyakovleonid.core.viewmodel

import androidx.lifecycle.ViewModel

interface AssistedVMFactory<V : ViewModel, P : BaseParams> {
    fun create(params: P): V
}