package com.project.gosdaq.dao.InterestingResponse

data class InterestingResponseDao(
    val isError: Boolean,
    val data: MutableList<InterestingResponseInformation>?,
    val message: String
)