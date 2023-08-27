package com.currencyconverter.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class ConvertCurrencyUseCaseTest {

    private val convertCurrencyUseCase = ConvertCurrencyUseCase()

    @Test
    fun `given two currency return converted result success`() {

        // GIVEN
        // two currency of base euro and
        val fromUSD = 1.079443
        val toEGP = 33.358881
        val amount = 1.0

        // WHEN
        // call conversion function
        val result = convertCurrencyUseCase.convertFromCurrencyToAnotherCurrency(
            amount = amount,
            fromCurrency = fromUSD,
            toCurrency = toEGP
        )

        //THEN
        // conversion success
        assertEquals(result, "30.90")
    }

    @Test
    fun `given two currency and amount is zero return converted result zero`() {

        // GIVEN
        // two currency of base euro and
        val fromUSD = 1.079443
        val toEGP = 33.358881
        val amount = 0.0

        // WHEN
        // call conversion function
        val result = convertCurrencyUseCase.convertFromCurrencyToAnotherCurrency(
            amount = amount,
            fromCurrency = fromUSD,
            toCurrency = toEGP
        )

        //THEN
        // conversion success
        assertEquals(result, "0.00")
    }
}