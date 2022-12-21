package com.project.gosdaq.repository.local

import android.content.Context
import com.project.gosdaq.data.room.InterestingDatabase
import com.project.gosdaq.data.room.InterestingEntity
import timber.log.Timber

class InterestingLocalDataSourceImpl(context: Context) : InterestingLocalDataSource {

    private val interestingDatabase = InterestingDatabase.getDatabase(context)

    override suspend fun loadInterestingDataList(): List<InterestingEntity> {
        return interestingDatabase.interestingDao().getAll()
    }

    override suspend fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        Timber.i("insert")
        interestingDatabase.interestingDao().insert(newInterestingEntity)
    }
}