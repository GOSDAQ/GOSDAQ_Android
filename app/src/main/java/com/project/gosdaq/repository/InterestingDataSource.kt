package com.project.gosdaq.repository

import com.project.gosdaq.dao.InterestingData

interface InterestingDataSource {
    interface LoadInterestingDataCallback {
        fun onLoaded(interestingDataList: MutableList<InterestingData>)
        fun onLoadFailed()
    }

    fun loadInterestingDataList(callback: LoadInterestingDataCallback)
    fun saveNewInterestingData(interestingData: InterestingData)
}