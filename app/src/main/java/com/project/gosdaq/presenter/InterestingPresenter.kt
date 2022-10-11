package com.project.gosdaq.presenter

import android.util.Log
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.interesting.InterestingResponseData
import com.project.gosdaq.data.available.IsAvailableTickerResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.repository.GosdaqRepository
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
    private val gosdaqRepository: GosdaqRepository
) : InterestingContract.InterestingPresenter {

    private lateinit var interestingRecyclerViewData: MutableList<InterestingResponseData>

    override suspend fun setInterestingDataList() {
        val localInterestingStockList = getLocalInterestingDataList()
        val stockInformation = getInterestingDataInformation(localInterestingStockList)

        withContext(Dispatchers.Main) {
            Timber.i("InterestingDataInformation ResponseCode: ${stockInformation.code} / ${stockInformation.msg}")

            if(stockInformation.code == 200){
                interestingRecyclerViewData = stockInformation.data
                interestingView.initInterestingRecyclerView(interestingRecyclerViewData)
                interestingView.setShimmerVisibility(false)
            }else{
                // Error로 인해 데이터를 받지 못했을 때 동작 필요
            }
        }
    }

    override suspend fun getLocalInterestingDataList(): List<InterestingEntity> {
        return suspendCoroutine { continuation ->
            gosdaqRepository.loadInterestingDataList(object :
                InterestingLocalDataSourceImpl.LoadInterestingDataCallback {
                override fun onLoaded(interestingDataList: List<InterestingEntity>) {
                    Timber.i("Local Data Size: ${interestingDataList.size}")
                    continuation.resume(interestingDataList)
                }
                override fun onLoadFailed() {
                    Timber.i("Failed to Local Data")
                    continuation.resume(listOf<InterestingEntity>())
                }
            })
        }
    }

    override suspend fun getInterestingDataInformation(stockNameList: List<InterestingEntity>): InterestingResponse {
        return suspendCoroutine { continuation ->
            gosdaqRepository.getStockInformation(
                stockNameList,
                object : GosdaqServiceDataSourceImpl.StockDataCallback {
                    override fun onResponse(interestingResponse: InterestingResponse) {
                        continuation.resume(interestingResponse)
                    }

                    override fun onFailure(e: Throwable) {
                        continuation.resumeWithException(e)
                    }
                })
        }
    }

    override suspend fun insertInterestingData(newInterestingData: String) {
        gosdaqRepository.insertInterestingData(InterestingEntity(newInterestingData))
        val data = getInterestingDataInformation(listOf(InterestingEntity(newInterestingData)))
        interestingRecyclerViewData.add(data.data[0])
        withContext(Dispatchers.Main){
            interestingView.updateInterestingRecyclerView()
        }
    }

    suspend fun isAvailableTicker(ticker: String, region: Region): IsAvailableTickerResponse {
        return suspendCoroutine { continuation ->
            gosdaqRepository.isAvailableTicker(
                ticker, region,
                object : GosdaqServiceDataSourceImpl.AvailableTickerCallback {
                    override fun onResponse(isAvailableTickerResponse: IsAvailableTickerResponse) {
                        Timber.i("isAvailableTicker ResponseCode: ${isAvailableTickerResponse.code} / ${isAvailableTickerResponse.msg}")
                        if(isAvailableTickerResponse.code != 500){
                            CoroutineScope(Dispatchers.IO).launch {
                                insertInterestingData(isAvailableTickerResponse.data.ticker)
                            }
                            continuation.resume(isAvailableTickerResponse)
                        }else {
                            Timber.i("$ticker is unavailable ticker")
                        }
                    }
                    override fun onFailure(e: Throwable) {
                        Timber.i("Failed to check, $ticker is available")
                        continuation.resumeWithException(e)
                    }
                }
            )
        }
    }
}