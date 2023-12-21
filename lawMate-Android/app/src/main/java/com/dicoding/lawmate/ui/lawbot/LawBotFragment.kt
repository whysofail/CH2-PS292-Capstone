package com.dicoding.lawmate.ui.lawbot

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.R
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.FragmentLawBotBinding
import com.dicoding.mystoryapp.preference.UserPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LawBotFragment : Fragment() {
    private var _binding: FragmentLawBotBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    private lateinit var userPref: UserPref

    private lateinit var viewModel:LawBotViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this@LawBotFragment).get(LawBotViewModel::class.java)

        _binding = FragmentLawBotBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPref = UserPref(requireContext())

        val btnKonsul = binding.btnChat
        btnKonsul.setOnClickListener() {
            chatBot()
        }

        return root
    }

    private fun chatBot() {
        val edChat = binding.edChat.text.toString()

        if (edChat.isNullOrEmpty()) {
            binding.edChat.error = "Field tidak boleh kosong"
        }else{
            viewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }

            lifecycleScope.launch {
                try {

                    if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                        val token = userPref.getUser().accessToken
                        if (token != null) {
                            viewModel.chat(token, edChat)
                        }

                        viewModel.chatResult.observe(viewLifecycleOwner) {
                            val responseChat = it.msg
                            val responseTag = it.tag

                            val fragmentTujuan = ResponseLawBotFragment()
                            // Mengirim data menggunakan setArguments
                            val args = Bundle()
                            args.putString("responseChat", responseChat)
                            args.putString("edChat", edChat)
                            args.putString("responseTag", responseTag)
                            fragmentTujuan.arguments = args

                            val transaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            transaction.addToBackStack(null)
                            transaction.replace(R.id.frame_layout, fragmentTujuan)
                            transaction.commit()
                        }
                    }

                }
                catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.d("cek eror", "${e.message}")
                }
                catch (e: HttpException) {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.msg

                    e.printStackTrace()
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Eror!")
                        setMessage(errorMessage)
                        setPositiveButton("Ulangi") { _, _ ->

                        }
                        create()
                        show()
                    }
                }
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