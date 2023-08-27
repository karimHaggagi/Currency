package com.currencyconverter.domain.usecase

import app.cash.turbine.test
import com.FakeData
import com.currencyconverter.data.datasource.remote.api.network.NetworkState
import com.currencyconverter.data.repository.FakeHomeDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetLatestCurrencyUseCaseTest {

    private lateinit var getLatestCurrencyUseCase: GetLatestCurrencyUseCase
    private lateinit var homeRepository: FakeHomeDataSource

    @Before
    fun init() {
        homeRepository = FakeHomeDataSource()
        getLatestCurrencyUseCase = GetLatestCurrencyUseCase(homeRepository)
    }

    @Test
    fun `get latest currency return data returned successfully`() = runTest {
        //When
        // get latest data
        getLatestCurrencyUseCase().test {
            //THEN
            // return loading then return success data
            assertEquals(awaitItem(), NetworkState.Loading)
            val data = (awaitItem() as NetworkState.Success).data
            assertEquals(FakeData.successModel, data)
            awaitComplete()
        }
    }

    @Test
    fun `get latest currency set should return error return data returned failed`() = runTest {
        //GIVEN
        // set return error true
        homeRepository.setShouldReturnError()
        //When
        // get latest data
        getLatestCurrencyUseCase().test {
            //THEN
            // return loading then return success failed
            assertEquals(awaitItem(), NetworkState.Loading)
            val data = (awaitItem() as NetworkState.Error).error
            assertEquals("Error", data)
            awaitComplete()
        }
    }
}