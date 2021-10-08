package com.mylongkenkai.drivesafe.state

import androidx.lifecycle.LiveData
import com.mylongkenkai.drivesafe.data.Exclusion

object  ExclusionsLiveData : LiveData<List<Exclusion>>(emptyList()) {

    fun add(exclusion: Exclusion) {
        this.value?.let {
            val list = it.toMutableList()
            list.add(exclusion)
            this.value = list
        }

    }

    fun delete(exclusion: Exclusion) {
        this.value?.let {
            val list = it.toMutableList()
            val initialLength = list.size
            list.remove(exclusion)
            if (list.size != initialLength) {
                this.value = list
            }
        }
    }
}