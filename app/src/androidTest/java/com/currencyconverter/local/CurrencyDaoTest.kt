package com.currencyconverter.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.currencyconverter.data.datasource.local.database.LocalDatabase
import com.currencyconverter.data.datasource.local.entitiy.LatestCurrencyEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LocalDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).build()
    }

    @Test
    fun insertCurrencyAndGetItem() = runTest {
        // GIVEN - Insert an item.
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
        database.currencyDao().insertToDatabase(item)

        // WHEN - Get the data by from the database.
        val loaded = database.currencyDao().getHistoricalData().first()[0]

        // THEN - The loaded data contains the expected values.

        assertEquals(loaded.id, item.id)
        assertEquals(loaded.date, item.date)
        assertEquals(loaded.fromCurrency.keys, (item.fromCurrency.keys))
        assertEquals(loaded.toCurrency.keys, (item.toCurrency.keys))
        assertEquals(loaded.fromCurrency.values.first(), item.fromCurrency.values.first())
        assertEquals(loaded.toCurrency.values.first(), item.toCurrency.values.first())

    }

    @Test
    fun deleteDataBeforeThreeDays() = runTest {
        // GIVEN - Insert two items one for current date and other for previous date.
        val toCurrency = mapOf("EGP" to "30.9")

        val fromCurrency = mapOf("USD" to "1.0")
        val otherCurrency = hashMapOf<String, String>()
        otherCurrency["AED"] = "1.03"
        otherCurrency["AFN"] = "2.03"
        otherCurrency["AWG"] = "4.03"

        val item1 = LatestCurrencyEntity(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            date = "2023-08-26",
            otherCurrency = otherCurrency,
            id = 1
        )
        database.currencyDao().insertToDatabase(item1)

        val item2 = LatestCurrencyEntity(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            date = "2023-08-22",
            otherCurrency = otherCurrency,
            id = 1
        )
        database.currencyDao().insertToDatabase(item2)

        // WHEN - delete previous date.
        database.currencyDao().deleteByDate("2023-08-22")

        // THEN - The loaded data contains the expected values.
        val loaded = database.currencyDao().getHistoricalData().first()

        assertEquals(loaded.size, 1)
        assertEquals(loaded[0].date, "2023-08-26")

    }

    @After
    fun closeDb() {
        database.close()
    }

}