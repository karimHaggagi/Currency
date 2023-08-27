package com.currencyconverter.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToDatabase(item: LatestCurrencyEntity)

    @Query("SELECT * FROM LatestCurrencyTable  ORDER BY Date DESC")
    fun getHistoricalData(): Flow<List<LatestCurrencyEntity>>

    @Query("DELETE FROM LatestCurrencyTable WHERE Date =:date")
    suspend fun deleteByDate(date: String)
}