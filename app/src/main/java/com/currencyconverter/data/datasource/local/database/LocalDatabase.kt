package com.currencyconverter.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currencyconverter.data.datasource.local.CurrencyDao
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity

@Database(entities = [LatestCurrencyEntity::class], version = 1)
@TypeConverters(MapTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}