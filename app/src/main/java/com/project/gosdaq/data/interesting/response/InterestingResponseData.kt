package com.project.gosdaq.data.interesting.response

data class InterestingResponseData(
    val data: String?,
    val open: Float?,
    val high: Float?,
    val low: Float?,
    val close: Float?,
    val adjClose: Float?,
    val volume: Int?
)