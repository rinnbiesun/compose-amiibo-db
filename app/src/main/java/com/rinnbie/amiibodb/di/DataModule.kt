package com.rinnbie.amiibodb.di

import com.rinnbie.amiibodb.repository.AmiiboRepository
import com.rinnbie.amiibodb.repository.RemoteAmiiboRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAmiiboListResourceRepository(
        amiiboRepository: RemoteAmiiboRepository
    ): AmiiboRepository
}