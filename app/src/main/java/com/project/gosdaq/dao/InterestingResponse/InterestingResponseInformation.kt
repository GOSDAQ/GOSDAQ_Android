package com.project.gosdaq.dao.InterestingResponse

data class InterestingResponseInformation(
    val id: String?,
    val rate: Float?,
    val price: Float?,
    val history: InterestingResponseHistory
)