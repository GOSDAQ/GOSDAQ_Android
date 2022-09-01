package com.project.gosdaq.data.interesting.response

data class InterestingResponseData(
    val ticker: String,
    val price: Float,
    val rate: Float,
    val history: MutableList<InterestingResponseHistory>,
    val cnt: Int
)