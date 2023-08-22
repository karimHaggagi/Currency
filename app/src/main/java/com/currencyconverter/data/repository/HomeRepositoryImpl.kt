package com.currencyconverter.data.repository

import com.currencyconverter.data.datasource.api.ApiService
import com.currencyconverter.data.datasource.api.dto.LatestCurrencyDTO
import com.currencyconverter.data.datasource.api.network.NetworkState
import com.currencyconverter.data.datasource.api.network.makeApiCall
import com.currencyconverter.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : HomeRepository {
    override suspend fun getLatestCurrency(): Flow<NetworkState<LatestCurrencyDTO>> {
        return makeApiCall(dispatcher) {
            apiService.getLatestCurrency()
        }
    }
}