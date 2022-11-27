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
): AmiiboRepository {
    override fun getAllAmiibos(): Flow<List<Amiibo>> {
        return remoteDataSource.getAllAmiibos()
    }

    override fun getAllSeries(): Flow<List<Series>> {
        return remoteDataSource.getAllSeries()
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