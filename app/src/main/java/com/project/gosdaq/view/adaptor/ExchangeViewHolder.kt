package com.project.gosdaq.view.adaptor

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.databinding.ItemExchangeViewHolderBinding
import timber.log.Timber

class ExchangeViewHolder(private val binding: ItemExchangeViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: InterestingResponseDataElement) {
        binding.someId.text = "원달러 ${data.price}원"
    }
}