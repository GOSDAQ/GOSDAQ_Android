package com.project.gosdaq.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InterestingDao {
    @Query("SELECT * FROM interesting_data")
    fun getAll(): List<InterestingEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestingEntity: InterestingEntity)
}