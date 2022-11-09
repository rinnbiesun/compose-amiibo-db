package com.rinnbie.amiibodb.repository

import com.rinnbie.amiibodb.data.AmiiboResponse
import com.rinnbie.amiibodb.data.SeriesResponse
import kotlinx.coroutines.flow.Flow

interface AmiiboRepository {
    fun getAllAmiibo(): Flow<AmiiboResponse>
    fun getAllSeries(): Flow<SeriesResponse>
}