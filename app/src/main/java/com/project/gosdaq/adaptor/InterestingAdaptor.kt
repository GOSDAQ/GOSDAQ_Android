package com.project.gosdaq.adaptor

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.gosdaq.activity.InterestingActivity
import com.project.gosdaq.data.interesting.InterestingResponseList
import com.project.gosdaq.databinding.ItemFavoriteRecyclerViewBinding
import timber.log.Timber

class InterestingAdaptor(private val data: MutableList<InterestingResponseList>) :
    RecyclerView.Adapter<InterestingAdaptor.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                Timber.i(adapterPosition.toString())
                val interestingActivityIntent = Intent(it.context, InterestingActivity::class.java)
                it.context.startActivity(interestingActivityIntent)
            }
        }

        fun bind(data: InterestingResponseList) {
            binding.stockName.text = data.name
            binding.stockName2.text = data.ticker

            if (data.rate.toDouble() >= 0.0){
                binding.stockPrice.text = "${data.price}"
                binding.stockPrice.setTextColor(Color.RED)
                binding.stockFluctuatingPercent.text = "(${data.rate}%)"
                binding.stockFluctuatingPercent.setTextColor(Color.RED)
            }else{
                binding.stockPrice.text = "${data.price}"
                binding.stockPrice.setTextColor(Color.BLUE)
                binding.stockFluctuatingPercent.text = "(${data.rate}%)"
                binding.stockFluctuatingPercent.setTextColor(Color.BLUE)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InterestingAdaptor.FavoriteViewHolder {
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