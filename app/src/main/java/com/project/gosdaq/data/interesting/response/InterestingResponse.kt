package com.project.gosdaq.data.interesting.response

data class InterestingResponse(
    val code: Int,
    val data: MutableList<InterestingResponseData>,
    val msg: String
)