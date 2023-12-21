package com.dicoding.lawmate.ui.lawyer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

    private lateinit var search :String
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

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                // Trigger search when text changes
                search()
            }
        })


        binding.icSearch.setOnClickListener{
            search()
        }
        search = binding.edSearch.text.toString()

        if (search.isNullOrEmpty()){
            setDataLawyers()
        }

    }

    private fun search() {
        lawyersViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        search = binding.edSearch.text.toString()

        if (search.isNullOrEmpty()){
            setDataLawyers()
        }else{
            lifecycleScope.launch {
                try {
                    lawyersViewModel.getSearchLawyers(search)

                    // Inisialisasi adapter
                    lawyersViewModel.lawyers.observe(viewLifecycleOwner) {
                        adapter = LawyersAdapter(it)
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
    }

    private fun setDataLawyers() {
        lawyersViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

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
                    "${e.message}",
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}