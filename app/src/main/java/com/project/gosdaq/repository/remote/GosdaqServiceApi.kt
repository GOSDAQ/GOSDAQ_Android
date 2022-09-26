package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import retrofit2.Call
import retrofit2.http.*

interface GosdaqServiceApi {

    @POST("/home/interest")
    fun getInterestingData(@Body data: HashMap<String, MutableList<String>>): Call<InterestingResponse>

    @GET("/common/search")
    fun isAvailableKrTicker(@Query("ticker") ticker: String, @Query("region") region: String = "KR"): Call<IsAvailableTickerResponse>

    @GET("/common/search")
    fun isAvailableUsTicker(@Query("ticker") ticker: String): Call<IsAvailableTickerResponse>
}