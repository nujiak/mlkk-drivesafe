package com.mylongkenkai.drivesafe.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { date.toString() }
    }

    @TypeConverter
    fun stringToDate(string: String?): Date? {
        return Date(string)
    }
}