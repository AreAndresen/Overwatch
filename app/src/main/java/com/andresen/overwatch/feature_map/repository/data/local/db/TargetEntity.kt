package com.andresen.overwatch.feature_map.repository.data.local.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "targets")
data class TargetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo val friendly: Boolean = false,
    @ColumnInfo val lat: Double,
    @ColumnInfo val lng: Double,
) : Parcelable