package com.project.gosdaq.contract

import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import kotlinx.coroutines.CoroutineScope

interface AddContract {
    interface AddView {
        fun showElement()
        fun onAddTickerSuccess()
        fun onAddTickerFailure(isDuplicated: Boolean, ticker: String)
    }

    interface AddPresenter {
        fun insertInterestingData(scope: CoroutineScope, ticker: String, region: Region)
    }
}