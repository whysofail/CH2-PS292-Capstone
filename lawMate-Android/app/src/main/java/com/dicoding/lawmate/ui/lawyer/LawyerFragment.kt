package com.dicoding.lawmate.ui.lawyer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.FragmentLawyerBinding
import com.dicoding.mystoryapp.preference.UserPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LawyerFragment : Fragment() {


    private var _binding: FragmentLawyerBinding? = null
    private val binding get() = _binding!!

    private lateinit var lawyersViewModel: LawyerViewModel

    private lateinit var adapter: LawyersAdapter

    private lateinit var userPref: UserPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lawyersViewModel =
            ViewModelProvider(this).get(LawyerViewModel::class.java)

        _binding = FragmentLawyerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.rvLawyers.setHasFixedSize(true)
        binding.rvLawyers.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            try {
                lawyersViewModel.getLawyers()

                // Inisialisasi adapter
                lawyersViewModel.lawyers.observe(viewLifecycleOwner) {
                    adapter = LawyersAdapter(it)
                    Log.e("Cek Data", "$it")
                    binding.rvLawyers.adapter = adapter
                }


            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Terjadi kesalahan: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("cek eror", "${e.message}")
            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}