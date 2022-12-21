package com.project.gosdaq.data.available

data class IsAvailableTickerResponse(
    val code: Int,
    val data: IsAvailableTickerResponseData?,
    val msg: String
)