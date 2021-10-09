package com.mylongkenkai.drivesafe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {
    @Query("SELECT * FROM record_table")
    fun getAll(): LiveData<List<Record>>

    @Insert
    fun insert(record: Record)

    @Delete
    fun delete(record: Record)
}