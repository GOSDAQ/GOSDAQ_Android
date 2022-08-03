package com.project.gosdaq.repository.remote

import android.util.Log
import com.project.gosdaq.BuildConfig
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GosdaqServiceDataSource : GosdaqServiceDataSourceImpl {
    private val TAG = this.javaClass.simpleName

    private val gosdaqBuilder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun getStockInformation(
        stockNameList: MutableList<String>,
        stockDataCallback: GosdaqServiceDataSourceImpl.StockDataCallback
    ) {

        val gosdaqService: GosdaqServiceApi = gosdaqBuilder.create(GosdaqServiceApi::class.java)

        val payload = hashMapOf<String, MutableList<String>>(
            "tickers" to stockNameList
        )

        gosdaqService.getInterestingData(payload)
            .enqueue(object : Callback<InterestingResponseDao> {
                var onFailureRetryCount = 0

                override fun onResponse(
                    call: Call<InterestingResponseDao>,
                    response: Response<InterestingResponseDao>
                ) {
                    stockDataCallback.onResponse(response.body()!!)
                }

                override fun onFailure(call: Call<InterestingResponseDao>, t: Throwable) {
                    onFailureRetryCount += 1
                    if (onFailureRetryCount < 3) {
                        retryOnFailure(call)
                    } else {
                        stockDataCallback.onFailure(t)
                    }
                }

                fun retryOnFailure(call: Call<InterestingResponseDao>) {
                    Log.i(TAG, "onFailure, retry $onFailureRetryCount times")
                    call.clone().enqueue(this)
                }
            })
    }
}