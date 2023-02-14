package com.rinnbie.amiibodb.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LastUpdated(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var lastUpdated: String = ""
)