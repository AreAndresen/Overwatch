package com.andresen.overwatch.feature_map.repository.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface MarkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(marker: MarkerEntity)

    @Delete
    suspend fun deleteMarker(marker: MarkerEntity)

    @Query("SELECT * FROM markers")
    fun getMarkers(): Flow<List<MarkerEntity>>
} */