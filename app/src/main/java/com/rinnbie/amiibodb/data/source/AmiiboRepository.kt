package com.rinnbie.amiibodb.data.source

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import kotlinx.coroutines.flow.Flow

interface AmiiboRepository {
    fun getAllAmiibos(forceUpdate: Boolean = false, seriesName: String = "all"): Flow<List<Amiibo>>
    fun getAllSeries(forceUpdate: Boolean = false): Flow<List<Series>>
    suspend fun saveAmiibo(amiibo: Amiibo)
    suspend fun saveSeries(series: Series)
    suspend fun deleteAllAmiibos()
    suspend fun deleteAllSeries()
    fun getLastUpdated(): Flow<String>
}