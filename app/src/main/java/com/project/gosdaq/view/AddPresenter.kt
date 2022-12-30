package com.project.gosdaq.view

import com.project.gosdaq.contract.AddContract
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.room.InterestingData
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.*
import timber.log.Timber

class AddPresenter(
    private val addView: AddContract.AddView,
    private val gosdaqRepository: GosdaqRepository
    ): AddContract.AddPresenter {

    override fun insertInterestingData(scope: CoroutineScope, ticker: String, region: Region) {
        scope.launch(Dispatchers.IO) {
            when (val isAvailableTicker = isAvailableTicker(ticker, region)) {
                null -> {
                    Timber.i("Cannot ticker")
                }
                else -> {
                    val newTicker = listOf(InterestingEntity(ticker=isAvailableTicker))
                    val interestingResponseDataElement = getInterestingDataInformation(newTicker).data.list[0]

                    gosdaqRepository.insertInterestingData(InterestingEntity(ticker=interestingResponseDataElement.ticker))

                    Timber.i(interestingResponseDataElement.toString())
                    InterestingData.interestingTickerList.add(0, interestingResponseDataElement)

                    delay(3000L)

                    addView.onAddTickerSuccess()
                }
            }
        }
    }


    private suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return gosdaqRepository.getStockInformation(stockNameList)
    }

    private suspend fun isAvailableTicker(ticker: String, region: Region): String? {
        InterestingData.interestingTickerList.find { interestingResponseList ->
            interestingResponseList.ticker.contains(ticker.uppercase())
        }?.let { return null }
        return gosdaqRepository.isAvailableTicker(ticker, region).data?.ticker
    }
}