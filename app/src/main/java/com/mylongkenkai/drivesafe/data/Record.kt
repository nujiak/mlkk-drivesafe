package com.mylongkenkai.drivesafe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class Record (
    @PrimaryKey val entryId : Int,

    val dateTime : String,
)
