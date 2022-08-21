package com.project.gosdaq.data.interesting.response

data class InterestingResponseInformation(
    val id: String?,
    val rate: Float?,
    val price: Float?,
    val history: InterestingResponseHistory
)