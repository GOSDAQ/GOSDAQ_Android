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
                when (val stockInformationResult = stockInformation.await()){
                    null -> {
                        Timber.i("Fail to receive (getStockInformation)")
                        interestingView.showToast("서버와 통신을 실패했어요.\n잠시 후 다시 시도해주세요.")
                        interestingView.initInterestingRecyclerView()
                        interestingView.setShimmerVisibility(false)
                    }
                    else -> {
                        Timber.i("InterestingDataInformation ResponseCode: ${stockInformationResult.code} / ${stockInformationResult.msg}")
                        when(isRefresh){
                            true -> {
                                InterestingData.interestingTickerList = InterestingData.interestingTickerList.subList(0, 1)
                                InterestingData.interestingTickerList += stockInformationResult.data.list.reversed()
                            }
                            false -> {
                                InterestingData.interestingTickerList += stockInformationResult.data.list.reversed()
                            }
                        }
                        interestingView.initInterestingRecyclerView()
                        interestingView.setShimmerVisibility(false)
                    }
                }

                when (val exchangeResponseResult = exchangeResponse.await()){
                    null -> {
                        Timber.i("Fail to receive (getExchange)")
                        InterestingData.interestingTickerList[0].price = 0.0F
                    }
                    else -> {
                        Timber.i("InterestingDataInformation ResponseCode: ${exchangeResponseResult.code} / ${exchangeResponseResult.msg}")
                        InterestingData.interestingTickerList[0].price = exchangeResponseResult.data.exchange.toFloat()
                        interestingView.initExchange(exchangeResponseResult.data.exchange.toString())
                    }
                }
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