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

        setContentView(binding.root)
    }
}