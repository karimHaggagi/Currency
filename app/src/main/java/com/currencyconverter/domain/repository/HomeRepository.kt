package com.currencyconverter.domain.repository

import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.data.datasource.remote.api.dto.LatestCurrencyDTO
import com.currencyconverter.data.datasource.remote.api.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getLatestCurrency(): Flow<NetworkState<LatestCurrencyDTO>>
    suspend fun insertToDatabase(item: LatestCurrencyEntity)
    suspend fun deleteDataByDate(date: String)
}