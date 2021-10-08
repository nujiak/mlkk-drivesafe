package com.mylongkenkai.drivesafe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.sensor.LinearAccelerometerLiveData
import com.mylongkenkai.drivesafe.state.BlockingStateLiveData

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val exclusions: MutableLiveData<List<Exclusion>> by lazy {
        MutableLiveData<List<Exclusion>>(emptyList())
    }

    val linearAccelerometerLiveData = LinearAccelerometerLiveData(app)

    fun getExclusions() : LiveData<List<Exclusion>> {
        return exclusions
    }

    fun addExclusion(exclusion : Exclusion) {
        val list = exclusions.value?.toMutableList()
        list?.add(exclusion)
        exclusions.value = list
    }

    fun removeExclusion(exclusion : Exclusion) {
        val list = exclusions.value?.toMutableList()
        list?.remove(exclusion)
        exclusions.value = list
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
}