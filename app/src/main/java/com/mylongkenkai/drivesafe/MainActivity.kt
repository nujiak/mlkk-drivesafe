package com.mylongkenkai.drivesafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mylongkenkai.drivesafe.databinding.ActivityMainBinding
import com.mylongkenkai.drivesafe.fragments.ExclusionsFragment
import com.mylongkenkai.drivesafe.fragments.LogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Add MainPagerAdapter to view pager
        binding.mainViewpager.adapter = MainPagerAdapter(this)

        setContentView(binding.root)
    }

    /**
     * Adapter for View Pager in MainActivity
     *
     * @constructor Creates a MainPagerAdapter
     *
     * @param activity MainActivity containing a ViewPager2
     */
    private inner class MainPagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> ExclusionsFragment()
            1 -> LogFragment()
            else -> throw IllegalArgumentException("Invalid viewpager position $position")
        }

    }
}