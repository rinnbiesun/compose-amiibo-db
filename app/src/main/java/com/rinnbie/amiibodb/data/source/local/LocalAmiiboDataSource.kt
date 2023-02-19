package com.rinnbie.amiibodb.data.source.local

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.LastUpdated
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.AmiiboDataSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalAmiiboDataSource @Inject constructor(private val amiiboDao: AmiiboDao) :
    AmiiboDataSource {

    override fun getAllAmiibos(): Flow<List<Amiibo>> {
        return amiiboDao.getAmiibos()
    }

    override fun getAllSeries(): Flow<List<Series>> {
        return amiiboDao.getSeries()
    }

    override fun getAmiibosBySeries(seriesName: String): Flow<List<Amiibo>> {
        return amiiboDao.getAmiibosBySeries(seriesName)
    }

    override fun getAmiibo(id: String): Flow<Amiibo> {
        return amiiboDao.getAmiibo(id)
    }

    override suspend fun saveAmiibo(amiibo: Amiibo) {
        amiibo.id = amiibo.head + amiibo.tail
        amiiboDao.insertAmiibo(amiibo)
    }

    override suspend fun saveSeries(series: Series) {
        amiiboDao.insertSeries(series)
    }

    override suspend fun deleteAllAmiibos() {
        amiiboDao.deleteAllAmiibos()
    }

    override suspend fun deleteAllSeries() {
        amiiboDao.deleteAllSeries()
    }

    override fun getLastUpdated(): Flow<String> {
        return flow {
            emit(amiiboDao.getLastUpdated()?.lastUpdated.orEmpty())
        }
    }

    override suspend fun saveLastUpdated(lastUpdated: String) {
        amiiboDao.getLastUpdated()?.let { lastUpdatedFromLocal ->
            lastUpdatedFromLocal.lastUpdated = lastUpdated
            amiiboDao.updateLastUpdated(lastUpdatedFromLocal)
        } ?: run {
            amiiboDao.insertLastUpdated(LastUpdated(lastUpdated = lastUpdated))
        }
    }
}