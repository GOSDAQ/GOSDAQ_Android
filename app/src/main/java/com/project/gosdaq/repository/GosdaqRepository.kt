package com.project.gosdaq.repository

import android.content.Context
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.exchange.ExchangeResponse
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GosdaqRepository @Inject constructor(
    private val localDataSource: InterestingLocalDataSource,
    private val remoteDataSource: GosdaqServiceDataSource
) : InterestingLocalDataSource, GosdaqServiceDataSource {

    override suspend fun loadInterestingDataList(): List<InterestingEntity>  {
        return localDataSource.loadInterestingDataList()
    }

    override suspend fun getStockInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return remoteDataSource.getStockInformation(stockNameList)
    }

    override suspend fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        localDataSource.insertInterestingData(newInterestingEntity)
    }

    override suspend fun deleteInterestingData(ticker: String) {
        localDataSource.deleteInterestingData(ticker)
    }

    override suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse {
        return remoteDataSource.isAvailableTicker(ticker, region)
    }

    override suspend fun getExchange(): ExchangeResponse {
        return remoteDataSource.getExchange()
    }
}