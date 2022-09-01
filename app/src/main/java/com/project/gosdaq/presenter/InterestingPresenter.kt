package com.project.gosdaq.presenter

import android.util.Log
import com.project.gosdaq.contract.InterestingContract
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
) : InterestingContract.InterestingPresenter {

    private val TAG = this.javaClass.simpleName

    override suspend fun initInterestingStockList() {
        val localInterestingStockList = loadInterestingData()
        val stockInformation = getStockInformation(localInterestingStockList)

        withContext(Dispatchers.Main) {
            Log.i(TAG, "isError: ${stockInformation.code}")
            Log.i(TAG, "message: ${stockInformation.msg}")

            if(stockInformation.code == 200){
                interestingView.setInterestingData(stockInformation.data)
                interestingView.setShimmerVisibility(false)
            }else{
                // Error로 인해 데이터를 받지 못했을 때 동작 필요
                Log.i(TAG, "isError")
            }
        }
    }

    override suspend fun loadInterestingData(): MutableList<String> {
        return suspendCoroutine { continuation ->
            GosdaqRepository.loadInterestingDataList(object :
                InterestingLocalDataSourceImpl.LoadInterestingDataCallback {
                override fun onLoaded(interestingDataList: MutableList<String>) {
                    continuation.resume(interestingDataList)
                }

                override fun onLoadFailed() {
                    continuation.resume(mutableListOf<String>("null"))
                }
            })
        }
    }

    override suspend fun getStockInformation(stockNameList: MutableList<String>): InterestingResponse {
        return suspendCoroutine { continuation ->
            GosdaqRepository.getStockInformation(
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

    override fun addInterestingData(newInterestingData: String) {
        GosdaqRepository.saveNewInterestingData(newInterestingData)
    }
}