package com.project.gosdaq.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InterestingEntity::class], version = 1)
abstract class InterestingDatabase : RoomDatabase() {
    abstract fun interestingDao(): InterestingDao
}