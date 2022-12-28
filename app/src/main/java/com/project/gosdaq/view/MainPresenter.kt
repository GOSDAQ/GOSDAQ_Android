package com.project.gosdaq.view

import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.*
import timber.log.Timber

class MainPresenter(
    private val interestingView: InterestingContract.InterestingView,
    private val gosdaqRepository: GosdaqRepository
) : InterestingContract.InterestingPresenter {

    private lateinit var interestingRecyclerViewData: MutableList<InterestingResponseDataElement>

    override fun setInterestingDataList(scope: CoroutineScope) {
        Timber.i("setInterestingDataList call")
        scope.launch(Dispatchers.IO) {
            val localInterestingStockList = getLocalInterestingDataList()
            val stockInformation = getInterestingDataInformation(localInterestingStockList)

            withContext(Dispatchers.Main) {
                Timber.i("InterestingDataInformation ResponseCode: ${stockInformation.code} / ${stockInformation.msg}")
                interestingRecyclerViewData = stockInformation.data.list
                interestingRecyclerViewData.reverse()
                interestingView.initInterestingRecyclerView(interestingRecyclerViewData)
                interestingView.setShimmerVisibility(false)
            }
        }
    }

    private suspend fun getLocalInterestingDataList(): List<InterestingEntity> {
        return gosdaqRepository.loadInterestingDataList()
    }

    private suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return gosdaqRepository.getStockInformation(stockNameList)
    }
}