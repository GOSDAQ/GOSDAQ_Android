package com.project.gosdaq.contract

import com.project.gosdaq.data.interesting.response.InterestingResponse
import com.project.gosdaq.data.interesting.response.InterestingResponseInformation

interface InterestingContract {
    interface InterestingView {
        fun setShimmerVisibility(visibility: Boolean)
        fun setInterestingData(interestingResponseInformation: MutableList<InterestingResponseInformation>)
    }

    interface InterestingPresenter {
        suspend fun initInterestingStockList()
        suspend fun loadInterestingData(): MutableList<String>
        suspend fun getStockInformation(stockNameList: MutableList<String>): InterestingResponse
        fun addInterestingData(newInterestingData: String)
    }
}