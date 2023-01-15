package com.project.gosdaq.view.adaptor

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.databinding.ItemInterestingViewHolderBinding

class InterestingViewHolder(
    private val binding: ItemInterestingViewHolderBinding,
    onItemClickListener: OnItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    init {
        itemView.setOnClickListener {
            onItemClickListener.onClick(adapterPosition)
        }
    }

    fun bind(data: InterestingResponseDataElement) {
        binding.stockName.text = data.name
        binding.stockName2.text = data.ticker

        if (data.rate.toDouble() >= 0.0){
            binding.stockPrice.text = "${data.price}"
            binding.stockPrice.setTextColor(Color.RED)
            binding.stockFluctuatingPercent.text = "${data.rate}%"
            binding.stockFluctuatingPercent.setTextColor(Color.RED)
        }else{
            binding.stockPrice.text = "${data.price}"
            binding.stockPrice.setTextColor(Color.BLUE)
            binding.stockFluctuatingPercent.text = "${data.rate}%"
            binding.stockFluctuatingPercent.setTextColor(Color.BLUE)
        }

    }
}