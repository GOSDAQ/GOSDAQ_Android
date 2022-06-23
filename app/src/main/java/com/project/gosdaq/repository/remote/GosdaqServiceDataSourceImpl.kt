package com.project.gosdaq.repository.remote

import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao

interface GosdaqServiceDataSourceImpl {
    interface StockDataCallback {
        fun onResponse(interestingResponseDao: InterestingResponseDao)
        fun onFailure()
    }

    fun getStockInformation(
        stockDataCallback: StockDataCallback,
        stockNameList: MutableList<String>
    )
}