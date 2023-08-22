package com.currencyconverter.domain.repository

import com.currencyconverter.data.datasource.api.dto.LatestCurrencyDTO
import com.currencyconverter.data.datasource.api.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getLatestCurrency(): Flow<NetworkState<LatestCurrencyDTO>>
}