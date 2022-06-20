package com.project.gosdaq.repository.local

import com.project.gosdaq.dao.InterestingData

interface InterestingLocalDataSourceImpl {
    interface LoadInterestingDataCallback {
        fun onLoaded(interestingDataList: MutableList<String>): MutableList<String>
        fun onLoadFailed(): MutableList<String>
    }

    fun loadInterestingDataList(loadInterestingDataCallback: LoadInterestingDataCallback): MutableList<String>
    fun saveNewInterestingData(interestingData: String)
}