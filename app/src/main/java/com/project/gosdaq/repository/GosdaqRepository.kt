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

    override fun loadInterestingDataList(loadInterestingDataCallback: InterestingLocalDataSourceImpl.LoadInterestingDataCallback): MutableList<String> {
        return interestingLocalDataSource.loadInterestingDataList(loadInterestingDataCallback)
    }

    override fun saveNewInterestingData(interestingData: String) {
        interestingLocalDataSource.saveNewInterestingData(interestingData)
    }

    override fun getStockInformation(
        stockDataCallback: GosdaqServiceDataSourceImpl.StockDataCallback,
        stockNameList: MutableList<String>
    ) {
        gosdaqServiceDataSource.getStockInformation(stockDataCallback, stockNameList)
    }
}