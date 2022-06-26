package com.project.gosdaq.repository.local

import com.project.gosdaq.dao.InterestingData

class InterestingLocalDataSource : InterestingLocalDataSourceImpl {

    private val interestingDataList: MutableList<String> = mutableListOf(
        "COIN",
        "SBUX"
    )

    override fun loadInterestingDataList(loadInterestingDataCallback: InterestingLocalDataSourceImpl.LoadInterestingDataCallback) {
        if (interestingDataList.isNotEmpty()) {
            loadInterestingDataCallback.onLoaded(interestingDataList)
        } else {
            loadInterestingDataCallback.onLoadFailed()
        }
    }

    override fun saveNewInterestingData(interestingData: String) {
        interestingDataList.add(interestingData)
    }
}