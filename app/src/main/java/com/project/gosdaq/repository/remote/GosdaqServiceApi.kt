package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.exchange.ExchangeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GosdaqServiceApi {

    @POST("/home/interest")
    suspend fun getInterestingData(@Body data: HashMap<String, List<String>>): Response<InterestingResponse>

    @GET("/common/search")
    suspend fun isAvailableKrTicker(@Query("ticker") ticker: String, @Query("region") region: String = "KR"): Response<IsAvailableTickerResponse>

    @GET("/common/search")
    suspend fun isAvailableUsTicker(@Query("ticker") ticker: String): Response<IsAvailableTickerResponse>

    @GET("/common/exchange")
    suspend fun getExchange(): Response<ExchangeResponse>
}