package com.project.gosdaq.data.interesting

data class InterestingResponseData(
    val list: MutableList<InterestingResponseDataElement>,
    val exchange: Double
)