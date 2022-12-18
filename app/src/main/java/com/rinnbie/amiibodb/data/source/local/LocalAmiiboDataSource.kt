package com.rinnbie.amiibodb.data.source.local

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.AmiiboDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalAmiiboDataSource @Inject constructor(private val amiiboDao: AmiiboDao) :
    AmiiboDataSource {

    override fun getAllAmiibos(): Flow<List<Amiibo>> {
        return amiiboDao.getAmiibos()
    }

    override fun getAllSeries(): Flow<List<Series>> {
        return amiiboDao.getSeries()
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
}