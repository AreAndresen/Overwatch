package com.andresen.overwatch.feature_map.repository.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
@Database(
    entities = [MarkerEntity::class],
    version = 1,
    exportSchema = false
    // autoMigrations = [ AutoMigration(from = 1, to = 2) ]
)
abstract class MarkerDatabase : RoomDatabase() {

    abstract fun markerDao(): MarkerDao

    companion object {
        fun createDao(context: Context): MarkerDao {
            return Room.databaseBuilder(
                context,
                MarkerDatabase::class.java,
                "marker_database.db"
            ).build().markerDao()
        }

    }
} */