package com.project.gosdaq.module

import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSource
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(
        gosdaqServiceDataSourceImpl: GosdaqServiceDataSourceImpl
    ): GosdaqServiceDataSource {
        Timber.i("RemoteDataSource Inject")
        return gosdaqServiceDataSourceImpl
    }
}