package com.currencyconverter.data.datasource.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currencyconverter.domain.model.Currency


@Entity(tableName = "LatestCurrencyTable")
data class LatestCurrencyEntity(
    @ColumnInfo(name = "FromCurrency")
    val fromCurrency: Map<String, String>,
    @ColumnInfo(name = "ToCurrency")
    val toCurrency: Map<String, String>,
    @ColumnInfo(name = "Date")
    val date: String = "",
    @ColumnInfo(name = "OtherCurrency")
    val otherCurrency: Map<String, String> = mapOf(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun LatestCurrencyEntity.fromCurrency(): Currency {
    val item = fromCurrency.iterator().next()
    return Currency(name = item.key, value = item.value)
}

fun LatestCurrencyEntity.toCurrency(): Currency {
    val item = toCurrency.iterator().next()
    return Currency(name = item.key, value = item.value)
}

fun LatestCurrencyEntity.toOtherCurrencyList(): List<Currency> {
    val currencyList: MutableList<Currency> = mutableListOf()
    val iterator = otherCurrency.iterator()
    do {
        val item = iterator.next()
        currencyList.add(Currency(name = item.key, value = item.value))
    } while (iterator.iterator().hasNext())
    return currencyList
}