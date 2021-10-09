package com.mylongkenkai.drivesafe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mylongkenkai.drivesafe.data.AppDatabase
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.data.Record
import com.mylongkenkai.drivesafe.sensor.LinearAccelerometerLiveData
import com.mylongkenkai.drivesafe.state.BlockingStateLiveData
import kotlinx.coroutines.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val exclusionsDb = AppDatabase.getInstance(app).exclusionDao()
    private val exclusions = exclusionsDb.getAll()

    private val recordsDb = AppDatabase.getInstance(app).recordDao()
    private val records = recordsDb.getAll()

    val linearAccelerometerLiveData = LinearAccelerometerLiveData(app)

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

    val isBlocking = BlockingStateLiveData

    /**
     * Attempt to start blocking activity
     */
    fun startBlocking() {
        Log.i(this::class.java.simpleName, "Start blocking; ${isBlocking.value}")
        isBlocking.startBlocking()
    }

    /**
     * Blocking activity is closed
     */
    fun finishBlocking() {
        isBlocking.stopBlocking()
    }

    private suspend fun insertExclusion(exclusion: Exclusion) = withContext(Dispatchers.IO) {
        exclusionsDb.insert(exclusion)
    }

    private suspend fun deleteExclusion(exclusion: Exclusion) = withContext(Dispatchers.IO) {
        exclusionsDb.delete(exclusion)
    }
}