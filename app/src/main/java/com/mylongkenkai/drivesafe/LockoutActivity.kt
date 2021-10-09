package com.mylongkenkai.drivesafe

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import com.mylongkenkai.drivesafe.databinding.ActivityLockoutBinding

class LockoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockoutBinding

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (checkNotificationPolicyAccess(notificationManager)) {
            notificationManager.onDOD()
        }
        binding = ActivityLockoutBinding.inflate(layoutInflater)

        model.linearAccelerometerLiveData.observe(this) {
            binding.lockoutAccelerometerReading.text = it.toString()
        }

        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        model.finishBlocking()
    }

    override fun onStop() {
        super.onStop()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (checkNotificationPolicyAccess(notificationManager)) {
            notificationManager.offDOD()
        }
    }

    private fun checkNotificationPolicyAccess(notificationManager: NotificationManager): Boolean {
        if (notificationManager.isNotificationPolicyAccessGranted) {
            return true
        } else {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }
        return false
    }

    private fun NotificationManager.onDOD() {
        this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
    }

    fun NotificationManager.offDOD() {
        this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    }

}