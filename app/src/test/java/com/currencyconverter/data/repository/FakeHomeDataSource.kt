package com.currencyconverter.data.repository

import com.FakeData
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.data.datasource.remote.api.dto.LatestCurrencyDTO
import com.currencyconverter.data.datasource.remote.api.network.NetworkState
import com.currencyconverter.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHomeDataSource(private val historicalData: MutableList<LatestCurrencyEntity>? = mutableListOf()) :
    HomeRepository {
    private var shouldReturnError = false


    fun setShouldReturnError() {
        shouldReturnError = true
    }

    override suspend fun getLatestCurrency(): Flow<NetworkState<LatestCurrencyDTO>> {

        return flow {
            emit(NetworkState.Loading)
            if (!shouldReturnError) {
                emit(
                    NetworkState.Success(FakeData.successDTO)
                )
            } else {
                emit(NetworkState.Error("Error"))
            }
        }
    }

    override suspend fun insertToDatabase(item: LatestCurrencyEntity) {
        historicalData?.add(item)
    }

    override suspend fun deleteDataByDate(date: String) {
        historicalData?.removeIf { it.date == date }
    }

    suspend fun getHistoricalData(): MutableList<LatestCurrencyEntity>? {
        return historicalData
    }

}