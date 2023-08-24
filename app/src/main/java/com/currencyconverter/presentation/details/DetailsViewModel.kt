package com.currencyconverter.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.domain.model.HistoricalDataModel
import com.currencyconverter.domain.usecase.GetHistoricalData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getHistoricalData: GetHistoricalData) :
    ViewModel() {

    private val _historicalDataStateFlow = MutableStateFlow<List<HistoricalDataModel>>(
        emptyList()
    )
    val historicalDataStateFlow = _historicalDataStateFlow.asStateFlow()


    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            getHistoricalData().collectLatest {
                _historicalDataStateFlow.emit(it)
            }
        }
    }
}