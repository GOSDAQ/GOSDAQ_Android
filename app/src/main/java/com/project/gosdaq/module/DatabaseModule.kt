package com.project.gosdaq.module

import android.content.Context
import androidx.room.Room
import com.project.gosdaq.data.room.InterestingDao
import com.project.gosdaq.data.room.InterestingDatabase
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): InterestingDatabase {
        Timber.i("Database Inject")
        return Room.databaseBuilder(
            context,
            InterestingDatabase::class.java,
            "interesting_database"
        ).build()
    }



    @Singleton
    @Provides
    fun provideInterestingDao(interestingDatabase: InterestingDatabase): InterestingDao {
        Timber.i("InterestingDao Inject")
        return interestingDatabase.interestingDao()
    }
}