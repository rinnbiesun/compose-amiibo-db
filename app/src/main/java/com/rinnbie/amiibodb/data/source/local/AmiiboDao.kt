package com.rinnbie.amiibodb.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface AmiiboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmiibo(amiibo: Amiibo)

    @Query("SELECT * FROM Amiibo")
    fun getAmiibos(): Flow<List<Amiibo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: Series)

    @Query("SELECT * FROM Series")
    fun getSeries(): Flow<List<Series>>

    @Query("DELETE FROM Amiibo")
    fun deleteAllAmiibos()

    @Query("DELETE FROM Series")
    fun deleteAllSeries()
}