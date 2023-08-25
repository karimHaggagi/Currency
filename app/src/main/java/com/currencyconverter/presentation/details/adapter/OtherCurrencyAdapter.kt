package com.currencyconverter.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.currencyconverter.databinding.OtherCurrencyItemBinding
import com.currencyconverter.domain.model.Currency

class OtherCurrencyAdapter :
    ListAdapter<Currency, OtherCurrencyAdapter.OtherCurrencyViewHolder>(
        DataDifferntiator
    ) {

    inner class OtherCurrencyViewHolder(private val binding: OtherCurrencyItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: Currency) {
            binding.tvToCurrency.text = item.name
            binding.tvToCurrencyValue.text = item.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCurrencyViewHolder {
        return OtherCurrencyViewHolder(
            OtherCurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OtherCurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    object DataDifferntiator : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(
            oldItem: Currency,
            newItem: Currency
        ): Boolean {
            return (oldItem.name == newItem.name) && (oldItem.value == newItem.value)
        }

        override fun areContentsTheSame(
            oldItem: Currency,
            newItem: Currency
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val HEADER_ITEM = 1
        const val ITEM = 2
    }
}