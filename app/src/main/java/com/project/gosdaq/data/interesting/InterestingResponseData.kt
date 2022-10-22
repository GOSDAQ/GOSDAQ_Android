package com.project.gosdaq.data.interesting

data class InterestingResponseData(
    val list: MutableList<InterestingResponseList>,
    val exchange: Double
)