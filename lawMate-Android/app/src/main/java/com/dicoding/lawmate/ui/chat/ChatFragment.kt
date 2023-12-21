package com.dicoding.lawmate.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lawmate.databinding.FragmentChatBinding
import com.dicoding.lawmate.ui.lawyer.LawyersAdapter
import com.dicoding.lawmate.ui.profile.ProfileViewModel
import com.dicoding.mystoryapp.preference.UserPref
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userPref: UserPref
    private lateinit var adapter: ConsultationAdapter
    private lateinit var adapterlawyer: LawyerConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.txtChat
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        userPref = UserPref(requireContext())

        binding.rvConsultation.setHasFixedSize(true)
        binding.rvConsultation.layoutManager = LinearLayoutManager(requireContext())


        setDataLawyers()

        return root
    }

    private fun setDataLawyers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        lifecycleScope.launch {
            try {
                if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                    val token = userPref.getUser().accessToken.toString()
                    profileViewModel.getUser(token)

                    profileViewModel.user.observe(viewLifecycleOwner) {
                        if (it.roleId == 1) {

                            lifecycleScope.launch {
                                viewModel.getConsultation(token)
                            }
                            // Inisialisasi adapter
                            viewModel.consultation.observe(viewLifecycleOwner) {

                                if (it.msg.isEmpty()) {
                                    binding.txtChat.visibility = View.VISIBLE
                                }

                                adapter = ConsultationAdapter(it.msg)
                                binding.rvConsultation.adapter = adapter
                            }
                        } else {
                            lifecycleScope.launch {
                                viewModel.getConsultationLawyer(token)
                            }

                            viewModel.consultation.observe(viewLifecycleOwner) {

                                if (it.msg.isEmpty()) {
                                    binding.txtChat.visibility = View.VISIBLE
                                }

                                adapterlawyer = LawyerConsultationAdapter(it.msg)
                                binding.rvConsultation.adapter = adapterlawyer
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

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