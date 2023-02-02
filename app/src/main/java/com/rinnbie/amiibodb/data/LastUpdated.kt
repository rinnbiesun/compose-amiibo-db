package com.rinnbie.amiibodb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastUpdated(
    @PrimaryKey val lastUpdated: String
)