package com.project.gosdaq.repository

import com.project.gosdaq.dao.InterestingData
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.remote.GosdaqServiceDataSource
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl


object GosdaqRepository : InterestingLocalDataSourceImpl, GosdaqServiceDataSourceImpl {

    private val interestingLocalDataSource: InterestingLocalDataSourceImpl =
        InterestingLocalDataSource()
    private val gosdaqServiceDataSource: GosdaqServiceDataSourceImpl = GosdaqServiceDataSource()

    override fun loadInterestingDataList(loadInterestingDataCallback: InterestingLocalDataSourceImpl.LoadInterestingDataCallback) {
        return interestingLocalDataSource.loadInterestingDataList(loadInterestingDataCallback)
    }

    override fun saveNewInterestingData(interestingData: String) {
        interestingLocalDataSource.saveNewInterestingData(interestingData)
    }

    override fun getStockInformation(
        stockNameList: MutableList<String>,
        stockDataCallback: GosdaqServiceDataSourceImpl.StockDataCallback
    ) {
        gosdaqServiceDataSource.getStockInformation(stockNameList, stockDataCallback)
    }
}