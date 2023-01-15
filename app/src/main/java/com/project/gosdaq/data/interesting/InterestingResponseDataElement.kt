package com.project.gosdaq.data.interesting

data class InterestingResponseDataElement(
    val ticker: String,
    val name: String,
    var price: Float,
    val rate: Float,
    val history: MutableList<InterestingResponseHistory>,
    val cnt: Int
)