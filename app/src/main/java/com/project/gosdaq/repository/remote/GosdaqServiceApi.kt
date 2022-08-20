package com.project.gosdaq.repository.remote

import com.project.gosdaq.data.interesting.response.InterestingResponse
import retrofit2.Call
import retrofit2.http.*

interface GosdaqServiceApi {

    @POST("/home/interest")
    fun getInterestingData(@Body data: HashMap<String, MutableList<String>>): Call<InterestingResponse>
}