package com.project.gosdaq.repository.local

import android.content.Context
import com.project.gosdaq.data.InterestingDatabase
import com.project.gosdaq.data.InterestingEntity
import java.lang.Exception

class InterestingLocalDataSource(context: Context) : InterestingLocalDataSourceImpl {

    private val interestingDatabase = InterestingDatabase.getDatabase(context)

    private lateinit var localInterestingDataList: List<InterestingEntity>
    
    override fun loadInterestingDataList(loadInterestingDataCallback: InterestingLocalDataSourceImpl.LoadInterestingDataCallback) {
        try{
            localInterestingDataList = interestingDatabase.interestingDao().getAll()
            if(localInterestingDataList.isNotEmpty()){
                loadInterestingDataCallback.onLoaded(localInterestingDataList)
            }else{
                loadInterestingDataCallback.onLoadFailed()
            }
        }catch (_:Exception){
            loadInterestingDataCallback.onLoadFailed()
        }
    }

    override fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        interestingDatabase.interestingDao().insert(newInterestingEntity)
    }
}