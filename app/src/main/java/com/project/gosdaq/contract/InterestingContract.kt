package com.project.gosdaq.contract

import com.project.gosdaq.dao.InterestingData

interface InterestingContract {
    interface InterestingView {
        fun setInterestingData(interestingDataList: MutableList<InterestingData>)
    }

    interface InterestingPresenter {
        fun loadInterestingData()

        fun addInterestingData(newInterestingData: InterestingData)
    }
}