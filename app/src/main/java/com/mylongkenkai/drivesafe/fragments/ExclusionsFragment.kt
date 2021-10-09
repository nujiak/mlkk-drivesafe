package com.mylongkenkai.drivesafe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylongkenkai.drivesafe.MainViewModel
import com.mylongkenkai.drivesafe.data.ExclusionAdapter
import com.mylongkenkai.drivesafe.databinding.FragmentExclusionsBinding

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
        binding.exclusionList.layoutManager = LinearLayoutManager(context)
        model.getExclusions().observe(viewLifecycleOwner) {
            val newAdapter = ExclusionAdapter(it, model::removeExclusion)
            binding.exclusionList.swapAdapter(newAdapter,false)
        }

        // Add FAB functionality
        binding.exclusionsFab.setOnClickListener {
            val inputDialog = InputDialog()
            inputDialog.show(childFragmentManager, "InputDialog")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}