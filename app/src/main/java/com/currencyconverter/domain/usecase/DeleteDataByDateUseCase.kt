package com.currencyconverter.domain.usecase

import com.currencyconverter.domain.repository.HomeRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class DeleteDataByDateUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    private var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    suspend operator fun invoke() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -4)
        val time = dateFormat.format(calendar.time)

        homeRepository.deleteDataByDate(time)
    }
}