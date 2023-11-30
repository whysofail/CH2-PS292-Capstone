package com.dicoding.lawmate.ui.lawyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.FragmentHomeBinding
import com.dicoding.lawmate.databinding.FragmentLawyerBinding
import com.dicoding.lawmate.ui.home.HomeViewModel

class LawyerFragment : Fragment() {


    private var _binding: FragmentLawyerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(LawyerViewModel::class.java)

        _binding = FragmentLawyerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.txtLawyer
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}