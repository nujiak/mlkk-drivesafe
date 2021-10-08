package com.mylongkenkai.drivesafe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mylongkenkai.drivesafe.R
import com.mylongkenkai.drivesafe.databinding.FragmentExclusionsBinding
import com.mylongkenkai.drivesafe.databinding.FragmentLogBinding

class ExclusionsFragment : Fragment() {

    private var _binding : FragmentExclusionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExclusionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}