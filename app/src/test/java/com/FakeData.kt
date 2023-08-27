package com

import com.currencyconverter.data.datasource.remote.api.dto.LatestCurrencyDTO
import com.currencyconverter.domain.model.LatestCurrencyModel

object FakeData {

    private var rates = hashMapOf<String, Double>().apply {
        this["EGP"] = 33.054
        this["USD"] = 1.08
        this["KWD"] = 2.05
    }

    val successDTO = LatestCurrencyDTO(
        base = "EUR",
        date = "25-08-2023",
        success = true,
        timestamp = 155105200,
        rates = rates
    )

    val successModel = LatestCurrencyModel(
        date = "25-08-2023",
        success = true,
        ratesNames = rates
    )
}