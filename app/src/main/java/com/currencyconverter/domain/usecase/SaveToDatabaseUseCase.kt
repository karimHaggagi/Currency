package com.currencyconverter.domain.usecase

import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.domain.repository.HomeRepository
import javax.inject.Inject

class SaveToDatabaseUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) {

    suspend operator fun invoke(
        fromCurrency: Pair<String, Double>,
        toCurrency: Pair<String, Double>,
        amount: String,
        result: String,
        date: String,
        otherCurrency: Map<String, Double>
    ) {
        val otherCurrencies = otherCurrency.asSequence().take(10).map { it.toPair() }.toMap()
        val convertedMap = hashMapOf<String, String>()
        otherCurrencies.forEach { (s, d) ->
            convertedMap[s] = convertCurrencyUseCase.convertFromCurrencyToAnotherCurrency(
                1.0,
                fromCurrency.second,
                d
            )
        }
        val item = LatestCurrencyEntity(
            fromCurrency = mapOf(fromCurrency),
            toCurrency = mapOf(toCurrency),
            amount = amount,
            convertedAmount = result,
            date = date,
            otherCurrency = convertedMap
        )
        homeRepository.insertToDatabase(item)
    }
}