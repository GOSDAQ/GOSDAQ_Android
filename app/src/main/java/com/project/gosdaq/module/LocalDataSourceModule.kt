package com.project.gosdaq.module

import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(interestingLocalDataSourceImpl: InterestingLocalDataSourceImpl): InterestingLocalDataSource {
        Timber.i("LocalDataSource Inject")
        return interestingLocalDataSourceImpl
    }
}