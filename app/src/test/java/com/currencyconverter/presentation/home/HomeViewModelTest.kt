package com.currencyconverter.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.currencyconverter.MainCoroutineRule
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.data.repository.FakeHomeDataSource
import com.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.currencyconverter.domain.usecase.DeleteDataByDateUseCase
import com.currencyconverter.domain.usecase.GetLatestCurrencyUseCase
import com.currencyconverter.domain.usecase.SaveToDatabaseUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: FakeHomeDataSource
    private lateinit var getLatestCurrencyUseCase: GetLatestCurrencyUseCase
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
    private lateinit var saveToDatabaseUseCase: SaveToDatabaseUseCase
    private lateinit var deleteDataByDateUseCase: DeleteDataByDateUseCase

    @Before
    fun init() {
        homeRepository = FakeHomeDataSource()
        convertCurrencyUseCase = ConvertCurrencyUseCase()
        getLatestCurrencyUseCase = GetLatestCurrencyUseCase(homeRepository)
        saveToDatabaseUseCase = SaveToDatabaseUseCase(homeRepository, convertCurrencyUseCase)
        deleteDataByDateUseCase = DeleteDataByDateUseCase(homeRepository)

        homeViewModel = HomeViewModel(
            getLatestCurrencyUseCase,
            convertCurrencyUseCase,
            saveToDatabaseUseCase,
            deleteDataByDateUseCase
        )
    }

    @Test
    fun `convert currency returned converted value`() = runTest {
        //GIVEN
        // set selected from currency
        homeViewModel.setSelectedFromCurrency("USD" to 1.09)

        //WHEN
        // selected to currency
        homeViewModel.setSelectedToCurrency("EGP" to 33.35)

        //THEN
        // data converted successfully and inserted to database
        homeViewModel.toCurrencyStateFlow.test {
            assertEquals(awaitItem(), "30.60")
        }

        val historicalData = homeRepository.getHistoricalData() ?: emptyList()

        assertEquals(historicalData[0].fromCurrency, mapOf("USD" to "1"))
        assertEquals(historicalData[0].toCurrency, mapOf("EGP" to "30.60"))

    }

    @Test
    fun `convert currency change amount returned converted value with new amount`() = runTest {
        //GIVEN
        // set selected from currency
        homeViewModel.setSelectedFromCurrency("USD" to 1.09)

        //WHEN
        // selected to currency and changed amount
        homeViewModel.setSelectedToCurrency("EGP" to 33.35)
        homeViewModel.setAmountFromCurrency("2")


        //THEN
        // data converted successfully and inserted to database
        assertEquals(homeViewModel.toCurrencyStateFlow.first(), "30.60")
        homeViewModel.toCurrencyStateFlow.test {
            assertEquals(awaitItem(), "61.19")

        }

    }

    @Test
    fun `convert currency change result amount returned amount value with new value`() = runTest {
        //GIVEN
        // set selected from currency
        homeViewModel.setSelectedFromCurrency("USD" to 1.09)

        //WHEN
        // selected to currency and changed amount
        homeViewModel.setSelectedToCurrency("EGP" to 33.35)
        homeViewModel.onConvertedValueChanged("61.19")


        //THEN
        // data converted successfully and inserted to database
        assertEquals(homeViewModel.fromCurrencyStateFlow.first(), "2.00")

    }
}