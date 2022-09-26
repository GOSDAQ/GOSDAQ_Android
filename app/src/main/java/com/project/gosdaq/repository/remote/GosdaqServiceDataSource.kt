package com.project.gosdaq.repository.remote

import com.project.gosdaq.BuildConfig
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
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
        stockNameList: List<InterestingEntity>,
        stockDataCallback: GosdaqServiceDataSourceImpl.StockDataCallback
    ) {

        val gosdaqService: GosdaqServiceApi = gosdaqBuilder.create(GosdaqServiceApi::class.java)

        val data = mutableListOf<String>()

        stockNameList.forEach{
            data.add(it.ticker)
        }

        val payload = hashMapOf<String, MutableList<String>>(
            "tickers" to data
        )

        gosdaqService.getInterestingData(payload)
            .enqueue(object : Callback<InterestingResponse> {
                var onFailureRetryCount = 0

                override fun onResponse(
                    call: Call<InterestingResponse>,
                    response: Response<InterestingResponse>
                ) {
                    stockDataCallback.onResponse(response.body()!!)
                }

                override fun onFailure(call: Call<InterestingResponse>, t: Throwable) {
                    onFailureRetryCount += 1
                    if (onFailureRetryCount < 3) {
                        retryOnFailure(call)
                    } else {
                        stockDataCallback.onFailure(t)
                    }
                }

                fun retryOnFailure(call: Call<InterestingResponse>) {
                    call.clone().enqueue(this)
                }
            })
    }

    override fun isAvailableTicker(
        ticker: String,
        region: String,
        availableTickerCallback: GosdaqServiceDataSourceImpl.AvailableTickerCallback
    ) {
        val gosdaqService: GosdaqServiceApi = gosdaqBuilder.create(GosdaqServiceApi::class.java)

        return when(region){
            "KR" -> {
                gosdaqService.isAvailableKrTicker(ticker, region)
                    .enqueue(object: Callback<IsAvailableTickerResponse>{
                        override fun onResponse(
                            call: Call<IsAvailableTickerResponse>,
                            response: Response<IsAvailableTickerResponse>
                        ) {
                            availableTickerCallback.onResponse(response.body()!!)
                        }

                        override fun onFailure(call: Call<IsAvailableTickerResponse>, t: Throwable) {
                            availableTickerCallback.onFailure(t)
                        }

                    })
            }

            else -> {
                gosdaqService.isAvailableUsTicker(ticker)
                    .enqueue(object: Callback<IsAvailableTickerResponse>{
                        override fun onResponse(
                            call: Call<IsAvailableTickerResponse>,
                            response: Response<IsAvailableTickerResponse>
                        ) {
                            availableTickerCallback.onResponse(response.body()!!)
                        }

                        override fun onFailure(call: Call<IsAvailableTickerResponse>, t: Throwable) {
                            availableTickerCallback.onFailure(t)
                        }

                    })
            }
        }
    }
}