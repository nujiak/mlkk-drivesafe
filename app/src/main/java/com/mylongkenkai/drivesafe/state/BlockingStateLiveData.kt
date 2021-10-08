package com.mylongkenkai.drivesafe.state

import androidx.lifecycle.LiveData

/**
 * Singleton LiveData holding the blocking state of the app
 */
object BlockingStateLiveData : LiveData<Boolean>(false) {
    fun startBlocking() {
        if (this.value != true) {
            this.value = true
        }
    }

    fun stopBlocking() {
        this.value = false
    }
}