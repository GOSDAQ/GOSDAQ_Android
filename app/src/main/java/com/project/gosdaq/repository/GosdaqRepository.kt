package com.project.gosdaq.repository

import android.content.Context
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSource
import timber.log.Timber

class GosdaqRepository(context: Context) : InterestingLocalDataSource, GosdaqServiceDataSource {

    companion object {
        private var instance: GosdaqRepository? = null
        fun getInstance(context: Context): GosdaqRepository {
            if (instance == null) {
                instance = GosdaqRepository(context)
            }
            return instance!!
        }
    }

    private val interestingLocalDataSource: InterestingLocalDataSource = InterestingLocalDataSourceImpl(context)
    private val gosdaqServiceDataSourceImpl: GosdaqServiceDataSource = GosdaqServiceDataSourceImpl()

    override suspend fun loadInterestingDataList(): List<InterestingEntity>  {
        return interestingLocalDataSource.loadInterestingDataList()
    }

    override suspend fun getStockInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return gosdaqServiceDataSourceImpl.getStockInformation(stockNameList)
    }

    override suspend fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        interestingLocalDataSource.insertInterestingData(newInterestingEntity)
    }

    override suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse {
        return gosdaqServiceDataSourceImpl.isAvailableTicker(ticker, region)
    }
}