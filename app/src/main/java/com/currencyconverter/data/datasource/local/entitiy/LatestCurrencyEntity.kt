package com.currencyconverter.data.datasource.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LatestCurrencyTable")
data class LatestCurrencyEntity(
    @ColumnInfo(name = "FromCurrency")
    val fromCurrency: Map<String, Double>,
    @ColumnInfo(name = "ToCurrency")
    val toCurrency: Map<String, Double>,
    @ColumnInfo(name = "Amount")
    val amount: String = "",
    @ColumnInfo(name = "ConvertedAmount")
    val convertedAmount: String = "",
    @ColumnInfo(name = "Date")
    val date: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)