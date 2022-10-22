package com.project.gosdaq.data.interesting

data class InterestingResponseList(
    val ticker: String,
    val name: String,
    val price: Float,
    val rate: Float,
    val history: MutableList<InterestingResponseHistory>,
    val cnt: Int
)