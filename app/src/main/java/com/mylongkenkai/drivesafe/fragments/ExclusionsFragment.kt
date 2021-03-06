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
            if (it.isEmpty()) {
                binding.exclusionsEmptyGroup.visibility = View.VISIBLE
                binding.exclusionList.visibility = View.GONE
            } else {
                binding.exclusionsEmptyGroup.visibility = View.GONE
                binding.exclusionList.visibility = View.VISIBLE

                val newAdapter = ExclusionAdapter(it, model::removeExclusion)
                newAdapter.setHasStableIds(true)
                binding.exclusionList.swapAdapter(newAdapter,false)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}