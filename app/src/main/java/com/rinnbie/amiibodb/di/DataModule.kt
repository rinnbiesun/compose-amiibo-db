package com.rinnbie.amiibodb.di

import com.rinnbie.amiibodb.data.source.AmiiboDataSource
import com.rinnbie.amiibodb.data.source.AmiiboRepository
import com.rinnbie.amiibodb.data.source.DefaultAmiiboRepository
import com.rinnbie.amiibodb.data.source.local.LocalAmiiboDataSource
import com.rinnbie.amiibodb.data.source.remote.RemoteAmiiboDataSource
import com.rinnbie.amiibodb.util.ConnectivityManagerNetworkMonitor
import com.rinnbie.amiibodb.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @RemoteDataSource
    fun bindsRemoteAmiiboDataSource(
        amiiboDataSource: RemoteAmiiboDataSource
    ): AmiiboDataSource

    @Binds
    @LocalDataSource
    fun bindsLocalAmiiboDataSource(
        amiiboRepository: LocalAmiiboDataSource
    ): AmiiboDataSource

    @Binds
    fun bindsDefaultAmiiboRepository(
        amiiboRepository: DefaultAmiiboRepository
    ): AmiiboRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource