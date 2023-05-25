package com.andresen.overwatch.feature_overview.repository.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarget(target: TargetEntity)

    @Delete
    suspend fun deleteTarget(target: TargetEntity)

    @Query("SELECT * FROM targets")
    fun getTargets(): Flow<List<TargetEntity>>
}