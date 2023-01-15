package com.project.gosdaq.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.databinding.ItemExchangeViewHolderBinding
import com.project.gosdaq.databinding.ItemInterestingViewHolderBinding

class InterestingAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<InterestingResponseDataElement> = mutableListOf()

    private lateinit var onItemClickListener: InterestingViewHolder.OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: InterestingViewHolder.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding = ItemInterestingViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return InterestingViewHolder(binding, onItemClickListener)
            }

            else -> {
                val binding = ItemExchangeViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ExchangeViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].ticker == "EXCHANGE_INFORMATION") 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as InterestingViewHolder).bind(data[position])
            else -> (holder as ExchangeViewHolder).bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: MutableList<InterestingResponseDataElement>) {
        val interestingDiffUtil = InterestingDiffUtil(data, newData)
        val result = DiffUtil.calculateDiff(interestingDiffUtil)

        with(data) {
            clear()
            addAll(newData)
        }

        result.dispatchUpdatesTo(this)
    }
}