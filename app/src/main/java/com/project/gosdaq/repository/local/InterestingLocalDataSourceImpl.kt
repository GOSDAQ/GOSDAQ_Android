package com.project.gosdaq.repository.local

import com.project.gosdaq.data.InterestingEntity

interface InterestingLocalDataSourceImpl {
    interface LoadInterestingDataCallback {
        fun onLoaded(interestingDataList: List<InterestingEntity>)
        fun onLoadFailed()
    }

    fun loadInterestingDataList(loadInterestingDataCallback: LoadInterestingDataCallback)
    fun insertInterestingData(newInterestingEntity: InterestingEntity)
}