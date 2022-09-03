package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.InterestingEntity
import com.project.gosdaq.data.interesting.response.InterestingResponse

interface GosdaqServiceDataSourceImpl {
    interface StockDataCallback {
        fun onResponse(interestingResponse: InterestingResponse)
        fun onFailure(e: Throwable)
    }

    fun getStockInformation(
        stockNameList: List<InterestingEntity>,
        stockDataCallback: StockDataCallback
    )
}