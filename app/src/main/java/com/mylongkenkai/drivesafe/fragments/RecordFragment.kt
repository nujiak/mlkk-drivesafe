package com.mylongkenkai.drivesafe.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylongkenkai.drivesafe.MainViewModel
import com.mylongkenkai.drivesafe.R
import com.mylongkenkai.drivesafe.data.ExclusionAdapter
import com.mylongkenkai.drivesafe.data.RecordAdapter
import com.mylongkenkai.drivesafe.databinding.FragmentLogBinding

class RecordFragment : Fragment() {

    private var _binding : FragmentLogBinding? = null
    private val binding get() = _binding!!

    private val model : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLogBinding.inflate(inflater, container, false)

        binding.recordList.layoutManager = LinearLayoutManager(context)
        model.getRecords().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recordsEmptyGroup.visibility = View.VISIBLE
                binding.recordList.visibility = View.GONE
            } else {
                binding.recordsEmptyGroup.visibility = View.GONE
                binding.recordList.visibility = View.VISIBLE

                val newAdapter = RecordAdapter(it)
                binding.recordList.swapAdapter(newAdapter,false)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}