package com.mylongkenkai.drivesafe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mylongkenkai.drivesafe.MainViewModel
import com.mylongkenkai.drivesafe.databinding.FragmentExclusionsBinding
import kotlin.random.Random

class ExclusionsFragment : Fragment() {

    private var _binding : FragmentExclusionsBinding? = null
    private val binding get() = _binding!!

    private val model : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExclusionsBinding.inflate(inflater, container, false)

        // Update body based on exclusions data
        model.getExclusions().observe(viewLifecycleOwner) {
            binding.exclusionsBodyText.setText(it.joinToString(", "))
        }

        // Add FAB functionality
        binding.exclusionsFab.setOnClickListener {
            model.addExclusion(Random.nextInt(90000000, 99999999).toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}