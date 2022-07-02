package com.project.gosdaq.presenter

import android.util.Log
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseDao
import com.project.gosdaq.repository.GosdaqRepository
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InterestingPresenter(
    private val interestingView: InterestingContract.InterestingView,
) : InterestingContract.InterestingPresenter {

    private val TAG = this.javaClass.simpleName

    suspend fun initInterestingStockList() {
        val localInterestingStockList = loadInterestingData()
        val stockInformation = getStockInformation(localInterestingStockList)

        withContext(Dispatchers.Main) {
            Log.i(TAG, "isError: ${stockInformation.isError}")
            Log.i(TAG, "message: ${stockInformation.message}")

            if(!stockInformation.isError){
                interestingView.setInterestingData(stockInformation.data)
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

    override suspend fun getStockInformation(stockNameList: MutableList<String>): InterestingResponseDao {
        return suspendCoroutine { continuation ->
            GosdaqRepository.getStockInformation(
                stockNameList,
                object : GosdaqServiceDataSourceImpl.StockDataCallback {
                    override fun onResponse(interestingResponseDao: InterestingResponseDao) {
                        continuation.resume(interestingResponseDao)
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