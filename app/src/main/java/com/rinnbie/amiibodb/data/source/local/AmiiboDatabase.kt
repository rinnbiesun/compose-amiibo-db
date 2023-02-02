package com.rinnbie.amiibodb.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.LastUpdated
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.util.AmiiboReleaseConverter

@Database(entities = [Amiibo::class, Series::class, LastUpdated::class], version = 1, exportSchema = false)
@TypeConverters(AmiiboReleaseConverter::class)
abstract class AmiiboDatabase : RoomDatabase() {
    abstract fun amiiboDao(): AmiiboDao
}