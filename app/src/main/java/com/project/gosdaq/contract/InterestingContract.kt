package com.project.gosdaq.contract

import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseInformation

interface InterestingContract {
    interface InterestingView {
        fun setShimmerVisibility(visibility: Boolean)
        fun setInterestingData(interestingResponseInformation: MutableList<InterestingResponseInformation>)
    }

    interface InterestingPresenter {
        suspend fun initInterestingStockList()
        suspend fun loadInterestingData(): MutableList<String>
        suspend fun getStockInformation(stockNameList: MutableList<String>): InterestingResponseDao
        fun addInterestingData(newInterestingData: String)
    }
}