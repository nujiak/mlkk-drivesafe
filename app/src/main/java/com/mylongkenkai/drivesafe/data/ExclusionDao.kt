package com.mylongkenkai.drivesafe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExclusionDao {
    @Query("SELECT * FROM exclusion_table")
    fun getAll(): LiveData<List<Exclusion>>

    @Insert
    fun insert(exclusion: Exclusion)

    @Delete
    fun delete(exclusion: Exclusion)
}