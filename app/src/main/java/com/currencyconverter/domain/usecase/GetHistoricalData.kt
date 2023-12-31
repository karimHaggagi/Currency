package com.currencyconverter.domain.usecase

import com.currencyconverter.data.datasource.local.entitiy.fromCurrency
import com.currencyconverter.data.datasource.local.entitiy.toCurrency
import com.currencyconverter.data.datasource.local.entitiy.toOtherCurrencyList
import com.currencyconverter.domain.model.HistoricalDataModel
import com.currencyconverter.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHistoricalData @Inject constructor(private val detailsRepository: DetailsRepository) {

    suspend operator fun invoke(): Flow<List<HistoricalDataModel>> {
        return detailsRepository.getHistoricalData().map { entityList ->
            val grouped = entityList.groupBy { it.date }
            val list: MutableList<HistoricalDataModel> = mutableListOf()

            grouped.keys.forEachIndexed { index, item ->
                list.add(HistoricalDataModel.HistoricalDataHeaderItem(index, item))


                grouped[item]?.toList()?.reversed()?.forEachIndexed { itemIndex, data ->
                    list.add(
                        HistoricalDataModel.HistoricalDataItem(
                            idItem = itemIndex,
                            fromCurrency = data.fromCurrency(),
                            toCurrency = data.toCurrency(),
                            otherCurrency = data.toOtherCurrencyList()
                        )
                    )
                }
            }
            list
        }
    }
}