package com.project.gosdaq.repository.remote

import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import java.lang.Exception

interface GosdaqServiceDataSourceImpl {
    interface StockDataCallback {
        fun onResponse(interestingResponseDao: InterestingResponseDao)
        fun onFailure(e: Throwable)
    }

    fun getStockInformation(
        stockNameList: MutableList<String>,
        stockDataCallback: StockDataCallback
    )
}