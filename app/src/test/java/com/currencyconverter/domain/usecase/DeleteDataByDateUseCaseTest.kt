package com.currencyconverter.domain.usecase

import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import com.currencyconverter.data.repository.FakeHomeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteDataByDateUseCaseTest {

    private lateinit var deleteDataByDateUseCase: DeleteDataByDateUseCase
    private lateinit var homeRepository: FakeHomeDataSource

    @Before
    fun init() {
        homeRepository = FakeHomeDataSource()
        deleteDataByDateUseCase = DeleteDataByDateUseCase(homeRepository)
    }

    @Test
    fun `delete data before last three days`() = runTest {
        //GIVEN
        // database contain data before last three days

        val toCurrency = mapOf("EGP" to "30.9")

        val fromCurrency = mapOf("USD" to "1.0")
        val otherCurrency = hashMapOf<String, String>()
        otherCurrency["AED"] = "1.03"
        otherCurrency["AFN"] = "2.03"
        otherCurrency["AWG"] = "4.03"

        val item = LatestCurrencyEntity(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            date = "2023-08-17",
            otherCurrency = otherCurrency,
            id = 1
        )
        homeRepository.insertToDatabase(item)


        val item2 = LatestCurrencyEntity(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            date = "2023-08-22",
            otherCurrency = otherCurrency,
            id = 1
        )
        homeRepository.insertToDatabase(item2)

        //WHEN
        // delete last data
        deleteDataByDateUseCase()

        //THEN
        // data which contain date before last three days deleted
        val data = homeRepository.getHistoricalData() ?: emptyList()
        assertEquals(data.size, 1)

    }
}