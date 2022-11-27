package com.rinnbie.amiibodb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Series(
    @PrimaryKey val key: String, val name: String
) {
    @Transient
    var defaultAmiibo: Amiibo? = null
}