package com.andresen.overwatch.feature_overview.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TargetEntity::class],
    version = 1,
    exportSchema = false
    //autoMigrations = [ AutoMigration(from = 1, to = 2) ]
)
abstract class TargetDatabase : RoomDatabase() {

    abstract fun targetDao(): TargetDao

    companion object {
        fun createDao(context: Context): TargetDao {
            return Room.databaseBuilder(
                context,
                TargetDatabase::class.java,
                "target_database.db"
            ).build().targetDao()
        }

    }
}