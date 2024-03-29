package com.project.gosdaq.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interesting_data")
data class InterestingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticker: String
)