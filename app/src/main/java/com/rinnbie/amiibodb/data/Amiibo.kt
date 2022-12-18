package com.rinnbie.amiibodb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Amiibo(
    @PrimaryKey var id: String,
    val amiiboSeries: String? = null,
    val character: String? = null,
    val gameSeries: String? = null,
    val head: String? = null,
    val image: String? = null,
    val name: String? = null,
    val release: Release? = null,
    val tail: String? = null,
    val type: String? = null,
)