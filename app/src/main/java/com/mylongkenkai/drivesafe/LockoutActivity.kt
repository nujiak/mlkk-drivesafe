package com.mylongkenkai.drivesafe

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mylongkenkai.drivesafe.data.Record
import com.mylongkenkai.drivesafe.databinding.ActivityLockoutBinding
import java.util.*


class LockoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockoutBinding

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.onDOD()
        val currentTime = Record(0,Date(), Tag.START)
        model.addRecord(currentTime)

        binding = ActivityLockoutBinding.inflate(layoutInflater)

        binding.lockoutAccelerometerReading.setText(R.string.lockout_screen)

        setContentView(binding.root)
    }
    
    override fun onStop() {
        super.onStop()
        val currentTime = Record(0, Date(), Tag.END)
        model.addRecord(currentTime)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.offDOD()

    }

    private fun NotificationManager.onDOD() {
        this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
    }

    fun NotificationManager.offDOD() {
        this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    }
}