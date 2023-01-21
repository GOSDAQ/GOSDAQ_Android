package com.project.gosdaq.contract

import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import kotlinx.coroutines.CoroutineScope

interface InterestingContract {
    interface InterestingView {
        fun setShimmerVisibility(visibility: Boolean)
        fun initInterestingRecyclerView()
        fun updateInterestingRecyclerView(newData: MutableList<InterestingResponseDataElement>)
        fun initExchange(exchange: String)
        fun showToast(message: String)
    }

    interface InterestingPresenter {
        fun setInterestingDataList(scope: CoroutineScope, isRefresh: Boolean = false)
        fun deleteTicker(scope: CoroutineScope, pos: Int)
    }
}