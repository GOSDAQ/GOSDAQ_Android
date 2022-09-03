package com.project.gosdaq.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InterestingEntity::class], version = 1)
abstract class InterestingDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: InterestingDatabase? = null
        fun getDatabase(context: Context): InterestingDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InterestingDatabase::class.java,
                        "interesting_database"
                    ).build()
                }
            }
            return instance!!
        }
    }

    abstract fun interestingDao(): InterestingDao
}