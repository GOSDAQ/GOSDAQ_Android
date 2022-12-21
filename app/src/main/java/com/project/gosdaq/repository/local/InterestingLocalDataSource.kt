package com.project.gosdaq.repository.local

import com.project.gosdaq.data.room.InterestingEntity

interface InterestingLocalDataSource {
    suspend fun loadInterestingDataList(): List<InterestingEntity>
    suspend fun insertInterestingData(newInterestingEntity: InterestingEntity)
}