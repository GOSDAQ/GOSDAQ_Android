package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region

interface GosdaqServiceDataSourceImpl {
    interface StockDataCallback {
        fun onResponse(interestingResponse: InterestingResponse)
        fun onFailure(e: Throwable)
    }

    interface AvailableTickerCallback {
        fun onResponse(isAvailableTickerResponse: IsAvailableTickerResponse)
        fun onFailure(e: Throwable)
    }

    fun getStockInformation(
        stockNameList: List<InterestingEntity>,
        stockDataCallback: StockDataCallback
    )

    fun isAvailableTicker(
        ticker: String,
        region: Region,
        availableTickerCallback: AvailableTickerCallback
    )
}