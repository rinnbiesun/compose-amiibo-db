package com.rinnbie.amiibodb.di

import com.rinnbie.amiibodb.api.AmiiboService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideAmiiboService(): AmiiboService {
        return AmiiboService.create()
    }
}