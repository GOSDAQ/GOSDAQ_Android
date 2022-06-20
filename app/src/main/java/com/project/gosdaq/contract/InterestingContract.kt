package com.project.gosdaq.contract

import com.project.gosdaq.dao.InterestingData
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseInformation

interface InterestingContract {
    interface InterestingView {
        fun setInterestingData(interestingResponseInformation: MutableList<InterestingResponseInformation>)
    }

    interface InterestingPresenter {
        fun loadInterestingData()

        fun addInterestingData(newInterestingData: String)
    }
}