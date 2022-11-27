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
        TODO("Not yet implemented")
    }

    override suspend fun saveSeries(series: Series) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAmiibos() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSeries() {
        TODO("Not yet implemented")
    }
}