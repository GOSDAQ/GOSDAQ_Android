package com.project.gosdaq.presenter

import android.util.Log
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.InterestingEntity
import com.project.gosdaq.data.interesting.response.InterestingResponse
import com.project.gosdaq.repository.GosdaqRepository
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
    private val gosdaqRepository: GosdaqRepository
) : InterestingContract.InterestingPresenter {

    private val TAG = this.javaClass.simpleName

    override suspend fun setInterestingDataList() {
        val localInterestingStockList = getLocalInterestingDataList()
        val stockInformation = getInterestingDataInformation(localInterestingStockList)

        withContext(Dispatchers.Main) {
            Log.i(TAG, "code: ${stockInformation.code}")
            Log.i(TAG, "msg: ${stockInformation.msg}")

            if(stockInformation.code == 200){
                interestingView.initInterestingRecyclerView(stockInformation.data)
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
                    Log.i(TAG, "Load data: $interestingDataList")
                    continuation.resume(interestingDataList)
                }
                override fun onLoadFailed() {
                    Log.i(TAG, "Failed to load data")
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
        // 유효한 정보인지 확인

        // 데이터 저장
        gosdaqRepository.insertInterestingData(InterestingEntity(newInterestingData))
    }
}