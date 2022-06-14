package com.project.gosdaq.repository

import com.project.gosdaq.dao.InterestingData

class InterestingLocalDataSource : InterestingDataSource {

    private val interestingDataList: MutableList<InterestingData> = mutableListOf(
        InterestingData("A1"),
        InterestingData("A2"),
        InterestingData("A3"),
        InterestingData("A4"),
        InterestingData("A5"),
        InterestingData("A6")
    )

    override fun loadInterestingDataList(callback: InterestingDataSource.LoadInterestingDataCallback) {
        if (interestingDataList.isNotEmpty()) {
            callback.onLoaded(interestingDataList)
        } else {
            callback.onLoadFailed()
        }
    }

    override fun saveNewInterestingData(interestingData: InterestingData) {
        interestingDataList.add(interestingData)
    }
}