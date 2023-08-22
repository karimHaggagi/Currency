package com.currencyconverter.data.datasource.api.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

suspend fun <T> makeApiCall(dispatcher: CoroutineDispatcher, block: suspend () -> T) = flow {
    emit(NetworkState.Loading)
    val result = block()
    emit(NetworkState.Success(result))
}.flowOn(dispatcher).catch { t ->
    emit(NetworkState.Error(t.message.toString()))
}


fun <T, R> Flow<NetworkState<T>>.mapResultTo(mapper: (T) -> R): Flow<NetworkState<R>> {
    return map {
        when (it) {
            is NetworkState.Loading -> NetworkState.Loading
            is NetworkState.Success -> NetworkState.Success(mapper.invoke(it.data))
            is NetworkState.Error -> NetworkState.Error(it.error)
        }
    }
}