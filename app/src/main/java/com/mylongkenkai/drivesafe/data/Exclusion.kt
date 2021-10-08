package com.mylongkenkai.drivesafe.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exclusion_table")
data class Exclusion(
    @PrimaryKey val phoneNumber : Int
) {
    override fun toString(): String = phoneNumber.toString()
}
