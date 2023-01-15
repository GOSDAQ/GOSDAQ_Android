package com.project.gosdaq.repository.local

import android.content.Context
import com.project.gosdaq.data.room.InterestingDao
import com.project.gosdaq.data.room.InterestingDatabase
import com.project.gosdaq.data.room.InterestingEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class InterestingLocalDataSourceImpl @Inject constructor(
    private val interestingDao: InterestingDao
    ) : InterestingLocalDataSource {

    override suspend fun loadInterestingDataList(): List<InterestingEntity> {
        return interestingDao.getAll()
    }

    override suspend fun insertInterestingData(newInterestingEntity: InterestingEntity) {
        interestingDao.insert(newInterestingEntity)
    }

    override suspend fun deleteInterestingData(ticker: String) {
        interestingDao.delete(ticker)
    }
}