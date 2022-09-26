package com.project.gosdaq.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.gosdaq.data.interesting.InterestingResponseData
import com.project.gosdaq.databinding.ItemFavoriteRecyclerViewBinding

class InterestingAdaptor(private val data:MutableList<InterestingResponseData>):
    RecyclerView.Adapter<InterestingAdaptor.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: InterestingResponseData) {
            binding.stockName.text = data.ticker
            binding.stockPrice.text = "Price = ${data.price}"
            binding.stockFluctuatingPercent.text = "${data.rate}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestingAdaptor.FavoriteViewHolder {
        val binding = ItemFavoriteRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestingAdaptor.FavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}