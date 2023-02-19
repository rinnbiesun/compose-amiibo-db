package com.rinnbie.amiibodb.data.source

import android.util.Log
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.local.LocalAmiiboDataSource
import com.rinnbie.amiibodb.data.source.remote.RemoteAmiiboDataSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultAmiiboRepository @Inject constructor(
    private val remoteDataSource: RemoteAmiiboDataSource,
    private val localDataSource: LocalAmiiboDataSource
) : AmiiboRepository {
    override fun getAllAmiibos(forceUpdate: Boolean, seriesName: String): Flow<List<Amiibo>> {
        if (seriesName == "all") {
            if (forceUpdate) {
                return getRemoteAllAmiibos()
            }
            return localDataSource.getAllAmiibos().flatMapConcat {
                if (it.isEmpty()) {
                    return@flatMapConcat getRemoteAllAmiibos()
                }
                return@flatMapConcat flow { emit(it) }
            }
        }
        return localDataSource.getAmiibosBySeries(seriesName)
    }

    override fun getAllSeries(forceUpdate: Boolean): Flow<List<Series>> {
        if (forceUpdate) return getRemoteAllSeries()
        return localDataSource.getAllSeries().flatMapConcat {
            if (it.isEmpty()) {
                return@flatMapConcat getRemoteAllSeries()
            }
            return@flatMapConcat flow { emit(it) }
        }
    }

    override fun getAmiibo(id: String): Flow<Amiibo> {
        return localDataSource.getAmiibo(id)
    }

    private fun getRemoteAllAmiibos() = remoteDataSource.getAllAmiibos().map { amiibos ->
        amiibos.onEach { amiibo -> localDataSource.saveAmiibo(amiibo) }
    }

    private fun getRemoteAllSeries() = remoteDataSource.getAllSeries().map { series ->
        series.onEach { series ->
            localDataSource.saveSeries(series)
        }
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

    override fun checkForceUpdate(): Flow<Boolean> {
        return localDataSource.getLastUpdated().flatMapConcat { lastUpdatedFromLocal ->
            remoteDataSource.getLastUpdated().map { lastUpdatedFromRemote ->
                Log.d("DefaultAmiiboRepository", "lastUpdatedFromLocal=$lastUpdatedFromLocal, lastUpdatedFromRemote=$lastUpdatedFromRemote")
                if (lastUpdatedFromLocal.isEmpty() || lastUpdatedFromLocal != lastUpdatedFromRemote) {
                    // when app is launched first time, force update is needed
                    localDataSource.saveLastUpdated(lastUpdatedFromRemote)
                    return@map true
                }
                return@map false
            }
        }
    }
}