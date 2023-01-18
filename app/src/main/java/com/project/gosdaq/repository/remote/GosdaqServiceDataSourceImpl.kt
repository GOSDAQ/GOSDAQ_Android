package com.project.gosdaq.repository.remote

import com.project.gosdaq.BuildConfig
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.exchange.ExchangeResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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

    override suspend fun getStockInformation(stockNameList: List<InterestingEntity>): InterestingResponse? {
        val payload = hashMapOf("tickers" to stockNameList.map { it.ticker })
        val requestResponse = try{
            gosdaqService.getInterestingData(payload)
        }catch (_: Exception){
            return null
        }
        return when (requestResponse.isSuccessful) {
            true -> requestResponse.body()!!
            false -> null
        }
    }

    override suspend fun getExchange(): ExchangeResponse? {
        val requestResponse = try{
            gosdaqService.getExchange()
        }catch (_: Exception){
            return null
        }
        return when (requestResponse.isSuccessful) {
            true -> requestResponse.body()!!
            false -> null
        }
    }

    override suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse? {
        return when (region) {
            is Region.KR -> {
                val requestResponse = try{
                    gosdaqService.isAvailableKrTicker(ticker, region.countryName)
                }catch (_: Exception){
                    return null
                }
                when(requestResponse.isSuccessful){
                    true -> requestResponse.body()!!
                    false -> null
                }
            }
            is Region.US -> {
                val requestResponse = try{
                    gosdaqService.isAvailableUsTicker(ticker)
                }catch (_: Exception){
                    return null
                }
                when(requestResponse.isSuccessful){
                    true -> requestResponse.body()!!
                    false -> null
                }
            }
        }
    }
}