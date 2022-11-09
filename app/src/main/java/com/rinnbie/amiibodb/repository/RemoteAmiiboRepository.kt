package com.rinnbie.amiibodb.repository

import com.rinnbie.amiibodb.api.AmiiboService
import com.rinnbie.amiibodb.data.AmiiboResponse
import com.rinnbie.amiibodb.data.SeriesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteAmiiboRepository @Inject constructor(private val service: AmiiboService) :
    AmiiboRepository {
    override fun getAllAmiibo(): Flow<AmiiboResponse> {
        return flow {
            emit(service.getAllAmiibo())
        }
    }

    override fun getAllSeries(): Flow<SeriesResponse> {
        return flow {
            emit(service.getAllSeries())
        }
    }
}