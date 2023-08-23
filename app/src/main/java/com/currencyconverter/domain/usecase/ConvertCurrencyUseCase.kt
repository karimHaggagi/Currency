package com.currencyconverter.domain.usecase

import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {


    fun convertFromCurrencyToAnotherCurrency(
        amount: Double,
        fromCurrency: Double,
        toCurrency: Double
    ): String {

        if (fromCurrency == 0.0) {
            return "0.0"
        }
        // Convert Currency to EUR
        val amountInEUR = amount / fromCurrency

        // Convert EUR to Another Currency
        val result = (amountInEUR * toCurrency)
        return String.format("%.2f", result)

    }

}