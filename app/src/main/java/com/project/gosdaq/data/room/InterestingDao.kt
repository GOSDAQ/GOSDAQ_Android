package com.project.gosdaq.data.room

import androidx.room.*

@Dao
interface InterestingDao {
    @Query("SELECT * FROM interesting_data")
    fun getAll(): List<InterestingEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestingEntity: InterestingEntity)

    @Query("DELETE FROM interesting_data WHERE ticker = :ticker")
    fun delete(ticker: String)
}