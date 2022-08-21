package com.project.gosdaq.repository.local

interface InterestingLocalDataSourceImpl {
    interface LoadInterestingDataCallback {
        fun onLoaded(interestingDataList: MutableList<String>)
        fun onLoadFailed()
    }

    fun loadInterestingDataList(loadInterestingDataCallback: LoadInterestingDataCallback)
    fun saveNewInterestingData(interestingData: String)
}