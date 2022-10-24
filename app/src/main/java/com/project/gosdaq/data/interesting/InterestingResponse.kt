package com.project.gosdaq.data.interesting

data class InterestingResponse(
    val code: Int,
    val data: InterestingResponseData,
    val msg: String
)