package com.mylongkenkai.drivesafe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mylongkenkai.drivesafe.MainViewModel
import com.mylongkenkai.drivesafe.data.Exclusion
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
            binding.exclusionsBodyText.text = it.joinToString(",\n")
        }

        // Add FAB functionality
        binding.exclusionsFab.setOnClickListener {
            model.addExclusion(Exclusion(Random.nextInt(10000000,99999999))) // change this random thing to a input thing
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}