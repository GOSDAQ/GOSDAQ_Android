package com.project.gosdaq.contract

import com.project.gosdaq.data.InterestingEntity
import com.project.gosdaq.data.interesting.response.InterestingResponse
import com.project.gosdaq.data.interesting.response.InterestingResponseData

interface InterestingContract {
    interface InterestingView {
        fun setShimmerVisibility(visibility: Boolean)
        fun initInterestingRecyclerView(interestingResponseData: MutableList<InterestingResponseData>)
        fun setFloatingActionButton()
    }

    interface InterestingPresenter {
        suspend fun setInterestingDataList()
        suspend fun getLocalInterestingDataList(): List<InterestingEntity>
        suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse
        suspend fun insertInterestingData(newInterestingData: String)
    }
}