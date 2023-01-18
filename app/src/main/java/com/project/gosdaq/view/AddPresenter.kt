package com.project.gosdaq.view

import com.project.gosdaq.contract.AddContract
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.room.InterestingEntity
import com.project.gosdaq.data.interesting.InterestingResponse
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.data.room.InterestingData
import com.project.gosdaq.repository.GosdaqRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class AddPresenter @AssistedInject constructor(
    private val gosdaqRepository: GosdaqRepository,
    @Assisted private val addView: AddContract.AddView
    ): AddContract.AddPresenter {

    @AssistedFactory
    interface AddPresenterFactory {
        fun create(
            @Assisted addView: AddContract.AddView
        ): AddPresenter
    }

    override fun insertInterestingData(scope: CoroutineScope, ticker: String, region: Region) {
        scope.launch(Dispatchers.IO) {
            when (val isAvailableTicker = isAvailableTicker(ticker, region)) {
                null -> {
                    withContext(Dispatchers.Main){
                        addView.onAddTickerFailure(false, ticker)
                    }
                }
                "IS_DUPLICATED" -> {
                    withContext(Dispatchers.Main){
                        addView.onAddTickerFailure(true, ticker)
                    }
                }
                else -> {
                    val newTicker = listOf(InterestingEntity(ticker=isAvailableTicker))

                    when(val getStockInformationResult = gosdaqRepository.getStockInformation(newTicker)){
                        null -> {
                            Timber.i("Fail to receive (getStockInformation)")
                        }
                        else -> {
                            val interestingResponseDataElement = getStockInformationResult.data.list[0]
                            gosdaqRepository.insertInterestingData(InterestingEntity(ticker=interestingResponseDataElement.ticker))

                            Timber.i(interestingResponseDataElement.toString())
                            InterestingData.interestingTickerList.add(1, interestingResponseDataElement)

                            withContext(Dispatchers.Main){
                                addView.onAddTickerSuccess()
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun isAvailableTicker(ticker: String, region: Region): String? {
        InterestingData.interestingTickerList.find { interestingResponseList ->
            interestingResponseList.ticker.contains(ticker.uppercase())
        }?.let {
            return "IS_DUPLICATED"
        }
        return when(val isAvailableTickerResult = gosdaqRepository.isAvailableTicker(ticker, region)){
            null -> {
                Timber.i("Fail to receive (isAvailableTicker)")
                null
            }else ->{
                isAvailableTickerResult.data?.ticker
            }
        }
    }
}