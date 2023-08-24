package com.currencyconverter.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.currencyconverter.data.datasource.remote.api.network.NetworkState
import com.currencyconverter.databinding.FragmentHomeBinding
import com.currencyconverter.domain.model.LatestCurrencyModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var isFromSpinnerClicked = false
    private var isToSpinnerClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        observeData()

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }
        return binding.root
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.latestCurrencyStateFlow.collectLatest { state ->
                    when (state) {
                        NetworkState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is NetworkState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            if (state.data.success) {
                                handleResponseData(state.data)
                            }

                        }

                        is NetworkState.Error -> {
                            binding.progressBar.visibility = View.GONE

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.toCurrencyStateFlow.collectLatest {
                    binding.etTo.setText(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.fromCurrencyStateFlow.collectLatest {
                    binding.etFrom.setText(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isFromSpinnerClicked = false
        isToSpinnerClicked = false
    }

    private fun handleResponseData(data: LatestCurrencyModel) {

        binding.tvDate.text = data.date

        val fromCurrencyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data.ratesNames.keys.toList()
        )


        binding.spinnerFrom.setOnTouchListener { view, motionEvent ->
            isFromSpinnerClicked = true
            false
        }
        binding.spinnerFrom.adapter = fromCurrencyAdapter
        binding.spinnerFrom.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFromSpinnerClicked) {
                    viewModel.setSelectedFromCurrency(data.ratesNames.toList()[position])
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val toCurrencyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data.ratesNames.keys.toList()
        )

        binding.spinnerTo.setOnTouchListener { view, motionEvent ->
            isToSpinnerClicked = true
            false
        }
        binding.spinnerTo.adapter = toCurrencyAdapter
        binding.spinnerTo.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isToSpinnerClicked) {
                    viewModel.setSelectedToCurrency(data.ratesNames.toList()[position])
                } // to close the onItemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}


        }


        binding.btnSwitch.setOnClickListener {
            val toSpinnerPosition = binding.spinnerTo.selectedItemPosition
            binding.spinnerTo.setSelection(binding.spinnerFrom.selectedItemPosition)
            binding.spinnerFrom.setSelection(toSpinnerPosition)

        }

        setTextChangedListener()
    }

    private fun setTextChangedListener() {

        binding.etFrom.doOnTextChanged { text, _, _, _ ->
            if (binding.etFrom.hasFocus()) {
                viewModel.setAmountFromCurrency(text.toString())
            }
        }

        binding.etTo.doOnTextChanged { text, _, _, _ ->
            if (binding.etTo.hasFocus()) {
                viewModel.onConvertedValueChanged(text.toString())
            }
        }

    }
}