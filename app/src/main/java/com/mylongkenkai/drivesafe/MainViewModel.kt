package com.mylongkenkai.drivesafe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mylongkenkai.drivesafe.data.AppDatabase
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.data.Record

import kotlinx.coroutines.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val exclusionsDb = AppDatabase.getInstance(app).exclusionDao()
    private val exclusions = exclusionsDb.getAll()

    private val recordsDb = AppDatabase.getInstance(app).recordDao()
    private val records = recordsDb.getAll()

    fun getExclusions() : LiveData<List<Exclusion>> {
        return exclusions
    }

    fun addExclusion(exclusion : Exclusion) = uiScope.launch {
        insertExclusion(exclusion)
    }

    fun removeExclusion(exclusion : Exclusion) = uiScope.launch {
        deleteExclusion(exclusion)
    }

    fun getRecords() : LiveData<List<Record>> {
        return records
    }

    private suspend fun insertExclusion(exclusion: Exclusion) = withContext(Dispatchers.IO) {
        exclusionsDb.insert(exclusion)
    }

    private suspend fun deleteExclusion(exclusion: Exclusion) = withContext(Dispatchers.IO) {
        exclusionsDb.delete(exclusion)
    }
}