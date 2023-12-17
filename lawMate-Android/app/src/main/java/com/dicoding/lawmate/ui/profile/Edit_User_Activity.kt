package com.dicoding.lawmate.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.lawmate.R
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.ActivityEditUserBinding
import com.dicoding.lawmate.ui.profile.Utils.reduceFileImage
import com.dicoding.lawmate.ui.profile.Utils.uriToFile
import com.dicoding.mystoryapp.preference.UserPref
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class Edit_User_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEditUserBinding
    private lateinit var userPref: UserPref

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
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!allPermissionsGranted()) {
            //request permission
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        userPref = UserPref(this)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val photo = intent.getStringExtra("photo")

        Glide.with(binding.root)
            .load(photo)
            .into(binding.ivUser)

        binding.edFirstName.setText(firstName)
        binding.edLastName.setText(lastName)

        binding.setPicture.setOnClickListener {
            startGallery()
        }
        binding.btnEdit.setOnClickListener {
            editUser()
        }


        val toolbar: MaterialToolbar = binding.topAppBar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun editUser() {
        currentImageUri?.let { uri ->
            showLoading(true)
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val _firstName = binding.edFirstName.text.toString()
            val _lastName = binding.edLastName.text.toString()
            val _email = intent.getStringExtra("email").toString()
            val _password = intent.getStringExtra("password").toString()


            val firstName = _firstName.toRequestBody("text/plain".toMediaType())
            val lastName = _lastName.toRequestBody("text/plain".toMediaType())
            val email = _email.toRequestBody("text/plain".toMediaType())
            val password = _password.toRequestBody("text/plain".toMediaType())

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                        val token = userPref.getUser().accessToken

                        val apiService = ApiConfig.getApiService(token)
                        val successResponse = apiService.editUser(firstName, lastName, email, password, multipartBody)
                        showToast(successResponse.msg)

                    }

                    showLoading(false)
                    onBackPressed()
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
        } ?: showToast("Silakan masukkan berkas gambar terlebih dahulu.")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
            binding.ivUserSet.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
