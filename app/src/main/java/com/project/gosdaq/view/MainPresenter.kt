package com.project.gosdaq.view

import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.room.InterestingData
import com.project.gosdaq.repository.GosdaqRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class MainPresenter @AssistedInject constructor(
    private var gosdaqRepository: GosdaqRepository,
    @Assisted private val interestingView: InterestingContract.InterestingView
) : InterestingContract.InterestingPresenter {

    @AssistedFactory
    interface MainPresenterFactory {
        fun create(
            @Assisted interestingView: InterestingContract.InterestingView
        ): MainPresenter
    }

    override fun setInterestingDataList(scope: CoroutineScope, isRefresh: Boolean) {
        scope.launch(Dispatchers.IO) {
            val localInterestingStockList = gosdaqRepository.loadInterestingDataList()
            val stockInformation = async { gosdaqRepository.getStockInformation(localInterestingStockList) }
            val exchangeResponse = async { gosdaqRepository.getExchange() }

            withContext(Dispatchers.Main) {
                Timber.i("InterestingDataInformation ResponseCode: ${stockInformation.await().code} / ${stockInformation.await().msg}")

                when(isRefresh){
                    true -> {
                        InterestingData.interestingTickerList = InterestingData.interestingTickerList.subList(0, 1)
                        InterestingData.interestingTickerList += stockInformation.await().data.list.reversed()
                        InterestingData.interestingTickerList[0].price = exchangeResponse.await().data.exchange.toFloat()
                    }
                    false -> {
                        InterestingData.interestingTickerList += stockInformation.await().data.list.reversed()
                        InterestingData.interestingTickerList[0].price = exchangeResponse.await().data.exchange.toFloat()
                    }
                }

                interestingView.initInterestingRecyclerView()
                interestingView.setShimmerVisibility(false)

                interestingView.initExchange(exchangeResponse.await().data.exchange.toString())
            }
        }
    }

    override fun deleteTicker(scope: CoroutineScope, pos: Int){
        scope.launch(Dispatchers.IO) {
            Timber.i(InterestingData.interestingTickerList[pos].ticker)
            gosdaqRepository.deleteInterestingData(InterestingData.interestingTickerList[pos].ticker)
            InterestingData.interestingTickerList.removeAt(pos)

            withContext(Dispatchers.Main){
                interestingView.updateInterestingRecyclerView(InterestingData.interestingTickerList)
            }
        }
    }
}