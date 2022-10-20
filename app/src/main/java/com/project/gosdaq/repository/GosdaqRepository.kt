package com.project.gosdaq.repository

import android.content.Context
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.remote.GosdaqServiceDataSource
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl

class GosdaqRepository(context: Context) : InterestingLocalDataSourceImpl, GosdaqServiceDataSourceImpl {

    companion object {
        private var instance: GosdaqRepository? = null
        fun getInstance(context: Context): GosdaqRepository {
            if (instance == null) {
                instance = GosdaqRepository(context)
            }
            return instance!!
        }
    }

    private val interestingLocalDataSource: InterestingLocalDataSourceImpl = InterestingLocalDataSource(context)
    private val gosdaqServiceDataSource: GosdaqServiceDataSourceImpl = GosdaqServiceDataSource()

    override fun loadInterestingDataList(loadInterestingDataCallback: InterestingLocalDataSourceImpl.LoadInterestingDataCallback) {
        return interestingLocalDataSource.loadInterestingDataList(loadInterestingDataCallback)
    }

    override fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        interestingLocalDataSource.insertInterestingData(newInterestingEntity)
    }

    override fun getStockInformation(
        stockNameList: List<InterestingEntity>,
        stockDataCallback: GosdaqServiceDataSourceImpl.StockDataCallback
    ) {
        gosdaqServiceDataSource.getStockInformation(stockNameList, stockDataCallback)
    }

    override fun isAvailableTicker(ticker: String, region: Region, availableTickerCallback: GosdaqServiceDataSourceImpl.AvailableTickerCallback) {
        gosdaqServiceDataSource.isAvailableTicker(ticker, region, availableTickerCallback)
    }
}