package com.currencyconverter.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.currencyconverter.databinding.FragmentDetailsBinding
import com.currencyconverter.domain.model.Currency
import com.currencyconverter.domain.model.HistoricalDataModel
import com.currencyconverter.presentation.details.adapter.HistoricalDataAdapter
import com.currencyconverter.presentation.details.adapter.OnCurrencyItemClickListener
import com.currencyconverter.presentation.details.adapter.OtherCurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(), OnCurrencyItemClickListener {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var adapter: HistoricalDataAdapter
    private lateinit var otherCurrencyAdapter: OtherCurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)

        adapter = HistoricalDataAdapter(this)
        binding.rvHistoricalData.adapter = adapter

        otherCurrencyAdapter = OtherCurrencyAdapter()
        binding.rvOtherCurrency.adapter = otherCurrencyAdapter
        observeData()
        return binding.root
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.historicalDataStateFlow.collectLatest {
                    handleResponse(it)
                }
            }
        }
    }

    private fun handleResponse(data: List<HistoricalDataModel>) {
        adapter.submitList(data)
    }

    override fun onCurrencyItemClick(otherCurrency: List<Currency>) {
        otherCurrencyAdapter.submitList(otherCurrency)
    }
}