package com.dicoding.lawmate.ui.authentication.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.R
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.ActivityRegistrationBinding
import com.dicoding.lawmate.ui.authentication.login.LoginActivity
import com.dicoding.lawmate.ui.authentication.login.LoginViewModel
import com.dicoding.mystoryapp.preference.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val registViewModel by viewModels<RegistrationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProsesRegist.setOnClickListener {

            val email = binding.edRegistEmail.text.toString()
            val password = binding.edRegistPassword.text.toString()
            val firstName = binding.edFirstName.text.toString()
            val lastName = binding.edLastName.text.toString()
            val confirmPassword = binding.edRegistRepeatPassword.text.toString()

            if (firstName.isNullOrEmpty()) {
                binding.edFirstName.error = "First Name tidak boleh kosong"
            } else if (lastName.isNullOrEmpty()) {
                binding.edLastName.error = "Last Name tidak boleh kosong"
            } else if (email.isNullOrEmpty()) {
                binding.edRegistEmail.error = "Email tidak boleh kosong"
            } else if (!isValidEmail(email)) {
                binding.edRegistEmail.error = "Email anda tidak valid"
            } else if (password.isNullOrEmpty()) {
                binding.edRegistPassword.error = "Password tidak boleh kosong"
            } else if (confirmPassword.isNullOrEmpty()) {
                binding.edRegistRepeatPassword.error = "Confirm Password tidak boleh kosong"
            } else {

                registViewModel.isLoading.observe(this) {
                    showLoading(it)
                }

                lifecycleScope.launch {
                    try {
                        registViewModel.registrasi(
                            firstName,
                            lastName,
                            email,
                            password,
                            confirmPassword
                        )

                        registViewModel.registerResult.observe(this@RegistrationActivity) {
                            it.msg?.let { msg ->
                                showToast(msg)
                            }
                        }
                        startActivity(Intent(applicationContext, LoginActivity::class.java))

                    } catch (e: HttpException) {
                        val jsonInString = e.response()?.errorBody()?.string()
                        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                        val errorMessage = errorBody.msg

                        e.printStackTrace()
                        AlertDialog.Builder(this@RegistrationActivity).apply {
                            setTitle("Registrasi Gagal!")
                            setMessage(errorMessage)
                            setPositiveButton("Ulangi") { _, _ ->

                            }
                            create()
                            show()
                        }
                        showLoading(false)
                    }
                }

            }

        }


        binding.tvLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}