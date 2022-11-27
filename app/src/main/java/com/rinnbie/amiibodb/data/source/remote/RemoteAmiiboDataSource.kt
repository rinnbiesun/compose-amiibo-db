package com.rinnbie.amiibodb.data.source.remote

import com.rinnbie.amiibodb.api.AmiiboService
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.AmiiboDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteAmiiboDataSource @Inject constructor(private val service: AmiiboService) :
    AmiiboDataSource {

    override fun getAllAmiibos(): Flow<List<Amiibo>> {
        return flow {
            emit(service.getAllAmiibo().amiibo)
        }
    }

    override fun getAllSeries(): Flow<List<Series>> {
        return flow {
            emit(service.getAllSeries().series)
        }
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