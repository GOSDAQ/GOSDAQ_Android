package com.project.gosdaq.repository.local

import com.project.gosdaq.dao.InterestingData

interface InterestingLocalDataSourceImpl {
    interface LoadInterestingDataCallback {
        fun onLoaded(interestingDataList: MutableList<String>)
        fun onLoadFailed()
    }

    fun loadInterestingDataList(loadInterestingDataCallback: LoadInterestingDataCallback)
    fun saveNewInterestingData(interestingData: String)
}