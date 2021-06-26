package com.github.belyakovleonid.core.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T, R> Flow<List<T>>.mapElements(crossinline mapAction: (T) -> R): Flow<List<R>> {
    return this.map { list -> list.map(mapAction) }
}