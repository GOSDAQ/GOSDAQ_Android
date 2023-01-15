package com.project.gosdaq.repository.remote

import com.project.gosdaq.BuildConfig
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.exchange.ExchangeResponse
import com.project.gosdaq.data.interesting.InterestingResponseData
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.sql.Time
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GosdaqServiceDataSourceImpl @Inject constructor() : GosdaqServiceDataSource {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    private val gosdaqBuilder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val gosdaqService: GosdaqServiceApi by lazy {
        gosdaqBuilder.create(GosdaqServiceApi::class.java)
    }

    override suspend fun getStockInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        val payload = hashMapOf("tickers" to stockNameList.map { it.ticker })
        val requestResponse = gosdaqService.getInterestingData(payload)

        when (requestResponse.isSuccessful) {
            true -> return requestResponse.body()!!
            false -> throw Exception("")
        }
    }

    override suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse {
        return when (region) {
            is Region.KR -> {
                val requestResponse = gosdaqService.isAvailableKrTicker(ticker, region.countryName)
                when(requestResponse.isSuccessful){
                    true -> requestResponse.body()!!
                    false -> throw Exception("")
                }
            }
            is Region.US -> {
                val requestResponse = gosdaqService.isAvailableUsTicker(ticker)
                when(requestResponse.isSuccessful){
                    true -> requestResponse.body()!!
                    false -> throw Exception("")
                }
            }
        }
    }

    override suspend fun getExchange(): ExchangeResponse {
        val requestResponse = gosdaqService.getExchange()
        return requestResponse.body()!!
    }
}