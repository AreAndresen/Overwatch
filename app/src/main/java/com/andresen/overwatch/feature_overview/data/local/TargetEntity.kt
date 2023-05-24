package com.andresen.overwatch.feature_overview.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "targets")
data class TargetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo val lat: Double,
    @ColumnInfo val lng: Double,
) : Parcelable