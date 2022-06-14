package com.project.gosdaq.repository

import com.project.gosdaq.dao.InterestingData

object InterestingDataRepository : InterestingDataSource {

    private val interestingLocalDataSource: InterestingDataSource = InterestingLocalDataSource()

    override fun loadInterestingDataList(callback: InterestingDataSource.LoadInterestingDataCallback) {
        interestingLocalDataSource.loadInterestingDataList(callback)
    }

    override fun saveNewInterestingData(interestingData: InterestingData) {
        interestingLocalDataSource.saveNewInterestingData(interestingData)
    }
}