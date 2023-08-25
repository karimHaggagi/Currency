package com.currencyconverter.presentation.details.adapter

import com.currencyconverter.domain.model.Currency

interface OnCurrencyItemClickListener {
    fun onCurrencyItemClick(otherCurrency: List<Currency>)
}