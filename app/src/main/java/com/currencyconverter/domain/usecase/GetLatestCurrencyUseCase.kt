package com.currencyconverter.domain.usecase

import com.currencyconverter.data.datasource.api.network.NetworkState
import com.currencyconverter.data.datasource.api.network.mapResultTo
import com.currencyconverter.domain.model.LatestCurrencyModel
import com.currencyconverter.domain.model.asLatestCurrencyModel
import com.currencyconverter.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestCurrencyUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): Flow<NetworkState<LatestCurrencyModel>> {
        return homeRepository.getLatestCurrency().mapResultTo { latestCurrencyDTO ->
            if (latestCurrencyDTO.success) {
                latestCurrencyDTO.asLatestCurrencyModel()
            } else {
                LatestCurrencyModel()
            }
        }
    }
}