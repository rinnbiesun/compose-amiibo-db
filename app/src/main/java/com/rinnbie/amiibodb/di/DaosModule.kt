package com.rinnbie.amiibodb.di

import com.rinnbie.amiibodb.data.source.local.AmiiboDao
import com.rinnbie.amiibodb.data.source.local.AmiiboDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAmiiboDao(
        database: AmiiboDatabase,
    ): AmiiboDao = database.amiiboDao()
}
