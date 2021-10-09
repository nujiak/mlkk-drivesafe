package com.mylongkenkai.drivesafe.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mylongkenkai.drivesafe.Tag
import java.util.*

@Entity(tableName = "record_table")
data class Record (
    @PrimaryKey(autoGenerate = true) val entryId : Int,

    val dateTime : Date,
    val type: Tag,
)
