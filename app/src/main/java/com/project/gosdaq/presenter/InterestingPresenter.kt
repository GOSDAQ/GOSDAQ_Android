package com.project.gosdaq.presenter

import android.util.Log
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.dao.InterestingData
import com.project.gosdaq.repository.InterestingDataRepository
import com.project.gosdaq.repository.InterestingDataSource

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
) : InterestingContract.InterestingPresenter {
    override fun loadInterestingData() {
        InterestingDataRepository.loadInterestingDataList(object :
            InterestingDataSource.LoadInterestingDataCallback {
            override fun onLoaded(interestingDataList: MutableList<InterestingData>) {
                interestingView.setInterestingData(interestingDataList)
            }

            override fun onLoadFailed() {
                Log.i("MainPresenter", "Not Interesting Data")
            }
        })
    }

    override fun addInterestingData(newInterestingData: InterestingData) {
        InterestingDataRepository.saveNewInterestingData(newInterestingData)
    }
}