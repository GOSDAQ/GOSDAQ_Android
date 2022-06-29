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


    suspend fun initInterestingStockList() {
        val localInterestingStockList = loadInterestingData()
        val stockInformation = getStockInformation(localInterestingStockList)
        withContext(Dispatchers.Main) {
            Log.i("InterestingPresenter", stockInformation.isError.toString())
            Log.i("InterestingPresenter", stockInformation.message)
            interestingView.setInterestingData(stockInformation.data!!)
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