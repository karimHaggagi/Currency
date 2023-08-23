package com.currencyconverter.data.datasource.api

import com.currencyconverter.data.datasource.api.dto.LatestCurrencyDTO
import com.currencyconverter.utils.EndPoint
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoint.GET_ALL_CURRENCY)
    suspend fun getLatestCurrency(
        @Query("access_key") key: String = "ad7abb74d2f91b2cb0c78156168fb98e",
        @Query("format") format: Int = 1
    ): LatestCurrencyDTO
}