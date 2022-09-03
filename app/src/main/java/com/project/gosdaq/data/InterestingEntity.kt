package com.project.gosdaq.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interesting_data")
data class InterestingEntity(
    @PrimaryKey
    @ColumnInfo(name = "ticker")
    val ticker: String
)