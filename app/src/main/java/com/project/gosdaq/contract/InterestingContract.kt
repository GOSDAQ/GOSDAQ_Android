package com.project.gosdaq.contract

import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseData

interface InterestingContract {
    interface InterestingView {
        fun setShimmerVisibility(visibility: Boolean)
        fun initInterestingRecyclerView(interestingResponseData: MutableList<InterestingResponseData>)
        fun setFloatingActionButton()
        fun updateInterestingRecyclerView()
    }

    interface InterestingPresenter {
        suspend fun setInterestingDataList()
        suspend fun getLocalInterestingDataList(): List<InterestingEntity>
        suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse
        suspend fun insertInterestingData(newInterestingData: String)
    }
}