package com.currencyconverter.domain.model


sealed class HistoricalDataModel {

    abstract val id: Int

    data class HistoricalDataHeaderItem(
        val idHeader: Int,
        val date: String
    ) : HistoricalDataModel() {
        override val id: Int
            get() = idHeader
    }


    data class HistoricalDataItem(
        val idItem: Int,
        val fromCurrencyName: String = "",
        val fromCurrencyValue: String = "",
        val toCurrencyName: String = "",
        val toCurrencyValue: String = ""
    ) : HistoricalDataModel() {
        override val id: Int
            get() = idItem
    }
}