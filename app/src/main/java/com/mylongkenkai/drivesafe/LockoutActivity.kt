package com.mylongkenkai.drivesafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mylongkenkai.drivesafe.databinding.ActivityLockoutBinding

class LockoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLockoutBinding

    private val model : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}