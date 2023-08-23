package com.currencyconverter.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.data.datasource.api.network.NetworkState
import com.currencyconverter.domain.model.LatestCurrencyModel
import com.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.currencyconverter.domain.usecase.GetLatestCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestCurrencyUseCase: GetLatestCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) :
    ViewModel() {

    private val _latestCurrencyStateFlow =
        MutableStateFlow<NetworkState<LatestCurrencyModel>>(NetworkState.Loading)
    val latestCurrencyStateFlow = _latestCurrencyStateFlow.asStateFlow()

    private val _toCurrencyStateFlow = MutableStateFlow("1")
    val toCurrencyStateFlow = _toCurrencyStateFlow.asStateFlow()

    private val _fromCurrencyStateFlow = MutableStateFlow("1")
    val fromCurrencyStateFlow = _fromCurrencyStateFlow.asStateFlow()

    private var fromCurrency = 0.0
    private var toCurrency = 0.0

    private var amount = "1"

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
        viewModelScope.launch {
            _fromCurrencyStateFlow.emit("1")
            amount = "1"
            _toCurrencyStateFlow.emit(convertToCurrency())

        }
    }

    fun setSelectedToCurrency(toCurrency: Double) {
        this.toCurrency = toCurrency

        viewModelScope.launch {
            _toCurrencyStateFlow.emit("1")
            _toCurrencyStateFlow.emit(convertToCurrency())
        }
    }


    private fun convertToCurrency(): String {
        val amount = try {
            amount.toDouble()
        } catch (e: Exception) {
            0.0
        }
        return convertCurrencyUseCase.convertFromCurrencyToAnotherCurrency(
            amount = amount,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency
        )

    }

    fun setAmountFromCurrency(amount: String?) {
        viewModelScope.launch {
            if (amount.isNullOrBlank()) {
                return@launch
            }
            this@HomeViewModel.amount = amount.ifEmpty { "1" }
            _toCurrencyStateFlow.emit(convertToCurrency())
        }
    }

    fun onConvertedValueChanged(convertedValue: String) {

        val amount = try {
            convertedValue.toDouble()
        } catch (e: Exception) {
            0.0
        }
        viewModelScope.launch {
            _fromCurrencyStateFlow.emit(
                convertCurrencyUseCase.convertFromCurrencyToAnotherCurrency(
                    amount = amount,
                    fromCurrency = toCurrency,
                    toCurrency = fromCurrency
                )
            )
        }
    }
}