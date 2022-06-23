package com.project.gosdaq.presenter

import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import com.project.gosdaq.repository.GosdaqRepository
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
) : InterestingContract.InterestingPresenter {
    override fun loadInterestingData() {

        val localInterestingData = GosdaqRepository.loadInterestingDataList(object :
            InterestingLocalDataSourceImpl.LoadInterestingDataCallback {
            override fun onLoaded(interestingDataList: MutableList<String>): MutableList<String> {
                return interestingDataList
            }

            override fun onLoadFailed(): MutableList<String> {
                return mutableListOf<String>("null")
            }
        })

        GosdaqRepository.getStockInformation(object: GosdaqServiceDataSourceImpl.StockDataCallback{
            override fun onResponse(interestingResponseDao: InterestingResponseDao) {
                interestingView.setInterestingData(interestingResponseDao.data!!)
            }
            override fun onFailure() {
            }
        }, localInterestingData)
    }

    override fun addInterestingData(newInterestingData: String) {
        GosdaqRepository.saveNewInterestingData(newInterestingData)
    }
}