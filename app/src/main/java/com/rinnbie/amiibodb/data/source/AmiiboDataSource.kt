package com.rinnbie.amiibodb.data.source

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import kotlinx.coroutines.flow.Flow

interface AmiiboDataSource {
    fun getAllAmiibos(): Flow<List<Amiibo>>
    fun getAllSeries(): Flow<List<Series>>
    suspend fun saveAmiibo(amiibo: Amiibo)
    suspend fun saveSeries(series: Series)
    suspend fun deleteAllAmiibos()
    suspend fun deleteAllSeries()
}