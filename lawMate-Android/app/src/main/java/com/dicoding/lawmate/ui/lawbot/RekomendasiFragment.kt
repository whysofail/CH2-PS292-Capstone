package com.dicoding.lawmate.ui.lawbot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.R
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.FragmentRekomendasiBinding
import com.dicoding.lawmate.databinding.FragmentResponseLawBotBinding
import com.dicoding.lawmate.ui.lawyer.LawyerFragment
import com.dicoding.lawmate.ui.lawyer.LawyersAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException


class RekomendasiFragment : Fragment() {
    private var _binding: FragmentRekomendasiBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: LawBotViewModel

    private lateinit var adapter: RekomendasiLawyersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(LawBotViewModel::class.java)


        _binding = FragmentRekomendasiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        binding.rvLawyers.setHasFixedSize(true)
        binding.rvLawyers.layoutManager = LinearLayoutManager(requireContext())

        showLawyersRecomendation()


        return root
    }


    private fun showLawyersRecomendation() {
        val edChat = arguments?.getString("edChat")
        val responseTag = arguments?.getString("responseTag")

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        lifecycleScope.launch {
            try {
                viewModel.getLawyers("seksual")

                // Inisialisasi adapter
                viewModel.lawyers.observe(viewLifecycleOwner) {
                    adapter = RekomendasiLawyersAdapter(it, responseTag!!, edChat!!)
                    Log.e("Cek Data", "$it")
                    binding.rvLawyers.adapter = adapter
                }


            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.msg

                Toast.makeText(requireContext(), "$errorMessage", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

