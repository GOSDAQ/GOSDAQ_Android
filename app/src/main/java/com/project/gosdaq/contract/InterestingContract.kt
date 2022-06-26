package com.project.gosdaq.contract

import com.project.gosdaq.dao.InterestingData
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseInformation

interface InterestingContract {
    interface InterestingView {
        fun setInterestingData(interestingResponseInformation: MutableList<InterestingResponseInformation>)
    }

    interface InterestingPresenter {
        suspend fun loadInterestingData(): MutableList<String>
        suspend fun getStockInformation(stockNameList: MutableList<String>): InterestingResponseDao
        fun addInterestingData(newInterestingData: String)
    }
}