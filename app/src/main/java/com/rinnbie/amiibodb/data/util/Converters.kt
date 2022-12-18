package com.rinnbie.amiibodb.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rinnbie.amiibodb.data.Release

class AmiiboReleaseConverter {
    @TypeConverter
    fun amiiboReleaseToString(release: Release): String =
        Gson().toJson(release)

    @TypeConverter
    fun stringToAmiiboRelease(value: String): Release =
        Gson().fromJson(value, Release::class.java)
}