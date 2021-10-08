package com.mylongkenkai.drivesafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mylongkenkai.drivesafe.data.Exclusion

class MainViewModel : ViewModel() {
    private val exclusions: MutableLiveData<List<Exclusion>> by lazy {
        MutableLiveData<List<Exclusion>>(emptyList())
    }

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
}