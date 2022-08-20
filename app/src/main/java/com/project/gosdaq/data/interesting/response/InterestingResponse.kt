package com.project.gosdaq.data.interesting.response

data class InterestingResponse(
    val isError: Boolean,
    val data: MutableList<InterestingResponseInformation>,
    val message: String
)