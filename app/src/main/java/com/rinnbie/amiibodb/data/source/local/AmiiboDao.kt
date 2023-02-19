package com.rinnbie.amiibodb.data.source.local

import androidx.room.*
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.LastUpdated
import com.rinnbie.amiibodb.data.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface AmiiboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmiibo(amiibo: Amiibo)

    @Query("SELECT * FROM Amiibo")
    fun getAmiibos(): Flow<List<Amiibo>>

    @Query("SELECT * FROM Amiibo WHERE amiiboSeries = :seriesName")
    fun getAmiibosBySeries(seriesName: String): Flow<List<Amiibo>>

    @Query("SELECT * FROM Amiibo WHERE id = :id")
    fun getAmiibo(id: String): Flow<Amiibo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: Series)

    @Query("SELECT * FROM Series")
    fun getSeries(): Flow<List<Series>>

    @Query("DELETE FROM Amiibo")
    fun deleteAllAmiibos()

    @Query("DELETE FROM Series")
    fun deleteAllSeries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLastUpdated(lastUpdated: LastUpdated)

    @Update
    suspend fun updateLastUpdated(lastUpdated: LastUpdated)

    @Query("SELECT * FROM LastUpdated LIMIT 1")
    suspend fun getLastUpdated(): LastUpdated?
}