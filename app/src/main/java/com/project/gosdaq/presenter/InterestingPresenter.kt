package com.project.gosdaq.presenter

import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.*
import timber.log.Timber

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
    private val gosdaqRepository: GosdaqRepository
) : InterestingContract.InterestingPresenter {

    private lateinit var interestingRecyclerViewData: MutableList<InterestingResponseDataElement>

    override fun setInterestingDataList(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            val localInterestingStockList = getLocalInterestingDataList()
            val stockInformation = getInterestingDataInformation(localInterestingStockList)

            withContext(Dispatchers.Main) {
                Timber.i("InterestingDataInformation ResponseCode: ${stockInformation.code} / ${stockInformation.msg}")
                interestingRecyclerViewData = stockInformation.data.list
                interestingRecyclerViewData.reverse()
                Timber.i("?")
                interestingView.initInterestingRecyclerView(interestingRecyclerViewData)
                interestingView.setShimmerVisibility(false)
            }
        }
    }

    override fun insertInterestingData(scope: CoroutineScope, ticker: String, region: Region) {
        scope.launch(Dispatchers.IO) {
            when (val isAvailableTicker = isAvailableTicker(ticker, region)) {
                null -> {
                    Timber.i("Cannot ticker")
                }
                else -> {
                    val newTicker = listOf(InterestingEntity(ticker=isAvailableTicker))
                    val interestingResponseDataElement = gosdaqRepository.getStockInformation(newTicker).data.list[0]

                    gosdaqRepository.insertInterestingData(InterestingEntity(ticker=interestingResponseDataElement.ticker))

                    withContext(Dispatchers.Main){
                        interestingRecyclerViewData.add(0, interestingResponseDataElement)
                        interestingView.updateInterestingRecyclerView(interestingRecyclerViewData)
                    }
                }
            }
        }
    }

    private suspend fun getLocalInterestingDataList(): List<InterestingEntity> {
        return gosdaqRepository.loadInterestingDataList()
    }

    private suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return gosdaqRepository.getStockInformation(stockNameList)
    }

    private suspend fun isAvailableTicker(ticker: String, region: Region): String? {
        interestingRecyclerViewData.find { interestingResponseList ->
            interestingResponseList.ticker.contains(ticker.uppercase())
        }?.let { return null }

        return gosdaqRepository.isAvailableTicker(ticker, region).data?.ticker
    }
}