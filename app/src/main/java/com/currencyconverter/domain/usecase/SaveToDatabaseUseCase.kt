package com.currencyconverter.domain.usecase

import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.domain.repository.HomeRepository
import javax.inject.Inject

class SaveToDatabaseUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(
        fromCurrency: Pair<String, Double>,
        toCurrency: Pair<String, Double>,
        amount: String,
        result: String,
        date: String
    ) {
        val item = LatestCurrencyEntity(
            fromCurrency = mapOf(fromCurrency),
            toCurrency = mapOf(toCurrency),
            amount = amount,
            convertedAmount = result,
            date = date
        )
        homeRepository.insertToDatabase(item)
    }
}