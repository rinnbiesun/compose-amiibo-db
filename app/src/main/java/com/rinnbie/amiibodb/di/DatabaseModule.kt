package com.rinnbie.amiibodb.di

import android.content.Context
import androidx.room.Room
import com.rinnbie.amiibodb.data.source.local.AmiiboDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesAmiiboDatabase(
        @ApplicationContext context: Context,
    ): AmiiboDatabase = Room.databaseBuilder(
        context, AmiiboDatabase::class.java, "amiibo-database"
    ).build()
}
