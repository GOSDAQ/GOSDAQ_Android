package com.project.gosdaq.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.project.gosdaq.data.interesting.InterestingResponseDataElement

class InterestingDiffUtil(
    private val oldData: MutableList<InterestingResponseDataElement>,
    private val newData: MutableList<InterestingResponseDataElement>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].ticker == newData[newItemPosition].ticker
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}