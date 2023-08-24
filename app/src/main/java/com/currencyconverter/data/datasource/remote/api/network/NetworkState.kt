package com.currencyconverter.data.datasource.remote.api.network

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<out R>(val data: R) : NetworkState<R>()
    data class Error(val error: String?) : NetworkState<Nothing>()
}
