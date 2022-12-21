package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.interesting.InterestingResponseData
import kotlinx.coroutines.CoroutineScope

interface GosdaqServiceDataSource {
    suspend fun getStockInformation(stockNameList: List<InterestingEntity>): InterestingResponse
    suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse
}