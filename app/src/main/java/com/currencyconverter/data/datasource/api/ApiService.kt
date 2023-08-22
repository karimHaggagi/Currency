package com.currencyconverter.data.datasource.api

import com.currencyconverter.data.datasource.api.dto.LatestCurrencyDTO
import com.currencyconverter.utils.EndPoint
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoint.GET_ALL_CURRENCY)
    suspend fun getLatestCurrency(
        @Query("access_key") key: String = "38cf91d856ee14577b46c17a0f344075",
        @Query("format") format: Int = 1
    ): LatestCurrencyDTO
}