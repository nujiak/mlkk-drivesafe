package com.mylongkenkai.drivesafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.databinding.ActivityMainBinding
import com.mylongkenkai.drivesafe.fragments.ExclusionsFragment
import com.mylongkenkai.drivesafe.fragments.InputDialog
import com.mylongkenkai.drivesafe.fragments.LogFragment
import java.lang.Exception
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.widget.Toast


class MainActivity : AppCompatActivity(),
    InputDialog.InputDialogListener {

    private lateinit var binding: ActivityMainBinding

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // get the notification manager system service
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (checkNotificationPolicyAccess(notificationManager)){
            Toast.makeText(this,"Do Not Disturb permission allowed.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Do Not Disturb permission not allowed.", Toast.LENGTH_SHORT).show()
        }

        val viewPager = binding.mainViewpager
        val btmNavBar = binding.mainBtmNavBar

        // Add MainPagerAdapter to view pager
        viewPager.adapter = MainPagerAdapter(this)

        // Connect view pager to bottom navigation bar
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> btmNavBar.selectedItemId = R.id.btm_nav_exclusions
                    1 -> btmNavBar.selectedItemId = R.id.btm_nav_log
                }
            }
        })

        // Connect bottom navigation bar to view pager
        btmNavBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btm_nav_exclusions -> viewPager.currentItem = 0
                R.id.btm_nav_log -> viewPager.currentItem = 1
            }
            true
        }

        model.linearAccelerometerLiveData.observe(this) {
            if (it > 20) {
                model.startBlocking()
            }
        }

        model.isBlocking.observe(this) { isBlocking ->
            if (isBlocking) {
                val lockoutIntent = Intent(this, LockoutActivity::class.java)
                startActivity(lockoutIntent)
            }
        }

        setContentView(binding.root)
    }

    override fun onDialogPositiveClick(dialogFragment: DialogFragment) {
        val editText = dialogFragment.dialog?.findViewById<EditText>(R.id.dialog_input_text_field)
        editText?.let {
            val input = editText.text
            try {
                val number = input.toString().toInt()
                model.addExclusion(Exclusion(number))
            } catch (e: Exception) {
                Snackbar.make(binding.root, R.string.invalid_number, Snackbar.LENGTH_SHORT).show()
            } finally {
                dialogFragment.dismiss()
            }
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }


    /**
     * Adapter for View Pager in MainActivity
     *
     * @constructor Creates a MainPagerAdapter
     *
     * @param activity MainActivity containing a ViewPager2
     */
    private inner class MainPagerAdapter(activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> ExclusionsFragment()
            1 -> LogFragment()
            else -> throw IllegalArgumentException("Invalid viewpager position $position")
        }

    }

    private fun checkNotificationPolicyAccess(notificationManager:NotificationManager):Boolean{
        if (notificationManager.isNotificationPolicyAccessGranted){
            return true
        }else{
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }
        return false
    }
}