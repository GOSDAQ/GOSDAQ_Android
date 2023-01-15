package com.project.gosdaq.data.exchange

data class ExchangeResponse(
    val code: Int,
    val data: ExchangeResponseData,
    val msg: String
)