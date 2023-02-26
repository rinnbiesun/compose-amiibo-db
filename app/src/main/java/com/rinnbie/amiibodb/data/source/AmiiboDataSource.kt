package com.rinnbie.amiibodb.data.source

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import kotlinx.coroutines.flow.Flow

interface AmiiboDataSource {
    fun getAllAmiibos(): Flow<List<Amiibo>>
    fun getAllSeries(): Flow<List<Series>>
    fun getAmiibo(id: String): Flow<Amiibo>
    fun getAmiibosBySeries(seriesName: String): Flow<List<Amiibo>>
    fun search(query: String): Flow<List<Amiibo>>
    suspend fun saveAmiibo(amiibo: Amiibo)
    suspend fun saveSeries(series: Series)
    suspend fun deleteAllAmiibos()
    suspend fun deleteAllSeries()
    fun getLastUpdated(): Flow<String>
    suspend fun saveLastUpdated(lastUpdated: String)
}