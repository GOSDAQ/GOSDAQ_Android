package com.project.gosdaq.repository.remote

import com.project.gosdaq.BuildConfig
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.interesting.InterestingResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.sql.Time

class GosdaqServiceDataSourceImpl : GosdaqServiceDataSource {

    val gosdaqBuilder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gosdaqService: GosdaqServiceApi by lazy {
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
                if(requestResponse.isSuccessful){
                    return requestResponse.body()!!
                }else{
                    Timber.i(requestResponse.message())
                    Timber.i(requestResponse.code().toString())
                    throw Exception("")
                }
            }
            is Region.US -> {
                val requestResponse = gosdaqService.isAvailableUsTicker(ticker)
                requestResponse.body()!!
            }
        }
    }
}