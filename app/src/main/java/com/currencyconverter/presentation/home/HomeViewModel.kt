package com.currencyconverter.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.data.datasource.api.network.NetworkState
import com.currencyconverter.domain.model.LatestCurrencyModel
import com.currencyconverter.domain.usecase.GetLatestCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getLatestCurrencyUseCase: GetLatestCurrencyUseCase) :
    ViewModel() {

    private val _latestCurrencyStateFlow =
        MutableStateFlow<NetworkState<LatestCurrencyModel>>(NetworkState.Loading)
    val latestCurrencyStateFlow = _latestCurrencyStateFlow.asStateFlow()

    var fromCurrency = 0.0
        private set
    var toCurrency = 0.0
        private set


    init {
        getLatestCurrency()
    }

    private fun getLatestCurrency() {
        viewModelScope.launch {
            getLatestCurrencyUseCase().collectLatest {
                _latestCurrencyStateFlow.emit(it)
            }
        }
    }

    fun setSelectedFromCurrency(fromCurrency: Double) {
        this.fromCurrency = fromCurrency
    }

    fun setSelectedToCurrency(toCurrency: Double) {
        this.toCurrency = toCurrency
    }
}