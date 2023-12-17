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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.R
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.FragmentLawBotBinding
import com.dicoding.lawmate.ui.lawbot.Utils.getImageUri
import com.dicoding.mystoryapp.preference.UserPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LawBotFragment : Fragment() {
    private var _binding: FragmentLawBotBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null


    private lateinit var userPref: UserPref

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!allPermissionsGranted()) {
            //request permission
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        val viewModel = ViewModelProvider(this@LawBotFragment).get(LawBotViewModel::class.java)

        _binding = FragmentLawBotBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnKonsul = binding.btnChat

        userPref = UserPref(requireContext())

        binding.imageOpen.setOnClickListener {
            startGallery()
        }

        binding.camera.setOnClickListener {
            startCamera()
        }

        btnKonsul.setOnClickListener() {
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
                                args.putString("responseTag", responseTag)
                                fragmentTujuan.arguments = args

                                val transaction =
                                    requireActivity().supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.frame_layout, fragmentTujuan)
                                transaction.commit()
                            }
                        }

                    } catch (e: HttpException) {
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//


    /// fun

//    private fun showLoading(isLoading: Boolean) {
//        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun redirect(){
//        val intent = Intent(this, StoryActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//        finish()
//    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this@LawBotFragment)
        currentImageUri?.let {
            launcherIntentCamera.launch(it)
        } ?: Log.e("Camera", "Error: currentImageUri is null")
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
            binding.previewImageView.isVisible = true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}