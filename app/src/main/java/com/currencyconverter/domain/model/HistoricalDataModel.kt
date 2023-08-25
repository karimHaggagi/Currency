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
        val fromCurrency: Currency = Currency(),
        val toCurrency: Currency = Currency(),
        val otherCurrency: List<Currency> = emptyList()
    ) : HistoricalDataModel() {
        override val id: Int
            get() = idItem
    }
}

data class Currency(
    val name: String = "",
    val value: String = ""
)