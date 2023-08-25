package com.currencyconverter.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.currencyconverter.databinding.HeaderItemBinding
import com.currencyconverter.databinding.HistoricalDataItemBinding
import com.currencyconverter.domain.model.HistoricalDataModel

class HistoricalDataAdapter(private val onCurrencyItemClickListener: OnCurrencyItemClickListener) :
    ListAdapter<HistoricalDataModel, ViewHolder>(
        DataDifferntiator
    ) {

    inner class HistoricalDataViewHolder(private val binding: HistoricalDataItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: HistoricalDataModel.HistoricalDataItem) {
            binding.model = item
            if (adapterPosition == 1) {
                onCurrencyItemClickListener.onCurrencyItemClick(item.otherCurrency)
            }

            binding.root.setOnClickListener {
                onCurrencyItemClickListener.onCurrencyItemClick(item.otherCurrency)
            }
        }
    }

    inner class HistoricalHeaderViewHolder(private val binding: HeaderItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: HistoricalDataModel.HistoricalDataHeaderItem) {
            binding.tvDate.text = item.date
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HistoricalDataModel.HistoricalDataHeaderItem -> 1
            is HistoricalDataModel.HistoricalDataItem -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            HEADER_ITEM -> {
                HistoricalHeaderViewHolder(
                    HeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM -> {
                HistoricalDataViewHolder(
                    HistoricalDataItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                HistoricalDataViewHolder(
                    HistoricalDataItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_ITEM -> {
                (holder as HistoricalHeaderViewHolder).bind(getItem(position) as HistoricalDataModel.HistoricalDataHeaderItem)

            }

            ITEM -> {
                (holder as HistoricalDataViewHolder).bind(getItem(position) as HistoricalDataModel.HistoricalDataItem)
            }
        }
    }


    object DataDifferntiator : DiffUtil.ItemCallback<HistoricalDataModel>() {
        override fun areItemsTheSame(
            oldItem: HistoricalDataModel,
            newItem: HistoricalDataModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HistoricalDataModel,
            newItem: HistoricalDataModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val HEADER_ITEM = 1
        const val ITEM = 2
    }

}