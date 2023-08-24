package com.currencyconverter.data.repository

import com.currencyconverter.data.datasource.local.database.LocalDatabase
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val localDatabase: LocalDatabase
) : DetailsRepository {
    override suspend fun getHistoricalData(): Flow<List<LatestCurrencyEntity>> {
        return localDatabase.currencyDao().getHistoricalData()
    }
}