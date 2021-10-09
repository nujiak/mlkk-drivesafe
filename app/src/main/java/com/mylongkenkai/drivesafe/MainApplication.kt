package com.mylongkenkai.drivesafe

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mylongkenkai.drivesafe.work.DetectionWorker

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Begin foreground speed detection
        val detectionWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DetectionWorker>().build()
        WorkManager.getInstance(applicationContext)
            .enqueue(detectionWorkRequest)
    }
}