package com.currencyconverter.domain.repository

import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getHistoricalData(): Flow<List<LatestCurrencyEntity>>
}