package com.rinnbie.amiibodb.data.source

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.local.LocalAmiiboDataSource
import com.rinnbie.amiibodb.data.source.remote.RemoteAmiiboDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultAmiiboRepository @Inject constructor(
    val remoteDataSource: RemoteAmiiboDataSource,
    val localDataSource: LocalAmiiboDataSource
) : AmiiboRepository {
    override fun getAllAmiibos(forceUpdate: Boolean): Flow<List<Amiibo>> {
        if (forceUpdate) {
            return remoteDataSource.getAllAmiibos()
        }
        return localDataSource.getAllAmiibos()
    }

    override fun getAllSeries(forceUpdate: Boolean): Flow<List<Series>> {
        if (forceUpdate) {
            return remoteDataSource.getAllSeries()
        }
        return localDataSource.getAllSeries()
    }

    override suspend fun saveAmiibo(amiibo: Amiibo) {
        localDataSource.saveAmiibo(amiibo)
    }

    override suspend fun saveSeries(series: Series) {
        localDataSource.saveSeries(series)
    }

    override suspend fun deleteAllAmiibos() {
        localDataSource.deleteAllAmiibos()
    }

    override suspend fun deleteAllSeries() {
        localDataSource.deleteAllSeries()
    }
}