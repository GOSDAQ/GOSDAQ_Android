package com.project.gosdaq.module

import com.project.gosdaq.data.room.InterestingDatabase
import com.project.gosdaq.repository.GosdaqRepository
import com.project.gosdaq.repository.local.InterestingLocalDataSource
import com.project.gosdaq.repository.local.InterestingLocalDataSourceImpl
import com.project.gosdaq.repository.remote.GosdaqServiceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRepository(
        localDataSourceImpl: InterestingLocalDataSourceImpl,
        remoteDataSourceImpl: GosdaqServiceDataSourceImpl
    ): GosdaqRepository {
        Timber.i("Repository Inject")
        return GosdaqRepository(localDataSourceImpl, remoteDataSourceImpl)
    }
}