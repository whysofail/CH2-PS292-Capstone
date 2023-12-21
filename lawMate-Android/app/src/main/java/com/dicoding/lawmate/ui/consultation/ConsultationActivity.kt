package com.dicoding.lawmate.ui.consultation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.ActivityConsultationBinding
import com.dicoding.lawmate.ui.consultation.Utils.reduceFileImage
import com.dicoding.lawmate.ui.consultation.Utils.uriToFile
import com.dicoding.lawmate.ui.lawbot.LawBotViewModel
import com.dicoding.mystoryapp.preference.UserPref
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class ConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationBinding

    private lateinit var userPref: UserPref
    private lateinit var viewModel:ConsultationViewModel

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            ConsultationActivity.REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            //request permission
            requestPermissionLauncher.launch(ConsultationActivity.REQUIRED_PERMISSION)
        }

        viewModel = ViewModelProvider(this).get(ConsultationViewModel::class.java)

        userPref = UserPref(this)

        val name_lawyer = intent.getStringExtra("name_lawyers")
        val id_lawyer = intent.getStringExtra("lawyer_id")
        val desc = intent.getStringExtra("desc")

        // Mendapatkan referensi ke toolbar dari layout
        val toolbar: MaterialToolbar = binding.topAppBarChat

        toolbar.setTitle("$name_lawyer")

        // Mengatur aksi ketika ikon navigasi diklik
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Fungsi ini akan kembali ke halaman sebelumnya
        }


        binding.edChat.text = Editable.Factory.getInstance().newEditable(desc)


        binding.imageOpen.setOnClickListener {
            startGallery()
        }

        binding.camera.setOnClickListener {
            startCamera()
        }
        binding.btnSend.setOnClickListener{
            sendConsultation()
        }


    }

    private fun sendConsultation() {
        val edChat = binding.edChat.text.toString()

        if (edChat.isNullOrEmpty()) {
            binding.edChat.error = "Field tidak boleh kosong"
        }else {
            viewModel.isLoading.observe(this) {
                showLoading(it)
            }
            val _desc = binding.edChat.text.toString()

            val _tag = intent.getStringExtra("tag").toString()
            val lawyer_id = intent.getStringExtra("lawyer_id").toString()


            val desc = _desc.toRequestBody("text/plain".toMediaType())
            val tag = _tag.toRequestBody("text/plain".toMediaType())

            currentImageUri?.let { uri ->

                val imageFile = uriToFile(uri, this).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "file",
                    imageFile.name,
                    requestImageFile
                )

                lifecycleScope.launch {
                    try {
                        if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                            val token = userPref.getUser().accessToken.toString()

                            viewModel.sendConsultation(token, lawyer_id, tag, desc, multipartBody)
                            showToast("Terkirim")

                            intentActivity()
                        }

                    } catch (e: HttpException) {
                        e.message?.let { showToast(it) }
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.msg?.let { showToast(it) }
                        showLoading(false)
                    } catch (e: Exception) {
                        e.message?.let { showToast(it) }
                        showLoading(false)
                    }
                }
            }?: run {
                lifecycleScope.launch {
                    try {
                        if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                            val token = userPref.getUser().accessToken.toString()

                            viewModel.sendConsultationNoFile(token, lawyer_id, tag, desc)
                            showToast("Terkirim")

                            intentActivity()
                        }

                    } catch (e: HttpException) {
                        e.message?.let { showToast(it) }
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.msg?.let { showToast(it) }
                        showLoading(false)
                    } catch (e: Exception) {
                        e.message?.let { showToast(it) }
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun intentActivity() {
        val intent = Intent(this@ConsultationActivity, MainActivity::class.java)
        intent.putExtra("openChatFragment", true)
        startActivity(intent)
        finish()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startCamera() {
        currentImageUri = Utils.getImageUri(this)
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


    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
            binding.previewImageView.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}