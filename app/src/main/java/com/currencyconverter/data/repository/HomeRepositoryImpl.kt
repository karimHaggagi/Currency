package com.currencyconverter.data.repository

import com.currencyconverter.data.datasource.local.database.LocalDatabase
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.data.datasource.remote.api.ApiService
import com.currencyconverter.data.datasource.remote.api.dto.LatestCurrencyDTO
import com.currencyconverter.data.datasource.remote.api.network.NetworkState
import com.currencyconverter.data.datasource.remote.api.network.makeApiCall
import com.currencyconverter.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher,
    private val localDatabase: LocalDatabase
) : HomeRepository {
    override suspend fun getLatestCurrency(): Flow<NetworkState<LatestCurrencyDTO>> {
        return makeApiCall(dispatcher) {
            apiService.getLatestCurrency()
        }
    }

    override suspend fun insertToDatabase(item: LatestCurrencyEntity) {
        localDatabase.currencyDao().insertToDatabase(item)
    }

    override suspend fun deleteDataByDate(date: String) {
        localDatabase.currencyDao().deleteByDate(date)
    }
}