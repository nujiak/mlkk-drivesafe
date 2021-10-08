package com.mylongkenkai.drivesafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val exclusions: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(emptyList())
    }

    fun getExclusions() : LiveData<List<String>> {
        return exclusions
    }

    fun addExclusion(exclusion : String) {
        val list = exclusions.value?.toMutableList()
        list?.add(exclusion)
        exclusions.value = list
    }

    fun removeExclusion(exclusion : String) {
        val list = exclusions.value?.toMutableList()
        list?.remove(exclusion)
        exclusions.value = list
    }
}