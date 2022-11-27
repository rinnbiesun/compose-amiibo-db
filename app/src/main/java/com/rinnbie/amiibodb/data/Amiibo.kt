package com.rinnbie.amiibodb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Amiibo(
    @PrimaryKey val id: String,
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val head: String,
    val image: String,
    val name: String,
    val release: Release,
    val tail: String,
    val type: String
)