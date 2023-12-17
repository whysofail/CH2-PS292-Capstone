package com.dicoding.lawmate.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.api.response.ErrorResponse
import com.dicoding.lawmate.databinding.ActivityLoginBinding
import com.dicoding.lawmate.ui.authentication.registration.RegistrationActivity
import com.dicoding.mystoryapp.preference.UserModel
import com.dicoding.mystoryapp.preference.UserPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var userPref: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        binding.btnProsesMasuk.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNullOrEmpty()) {
                binding.edLoginEmail.error = "Email tidak boleh kosong"
            } else if (!isValidEmail(email)) {
                binding.edLoginEmail.error = "Email anda tidak valid"
            } else if (password.isNullOrEmpty()) {
                binding.edLoginPassword.error = "Password tidak boleh kosong"
            } else {
                loginViewModel.isLoading.observe(this) {
                    showLoading(it)
                }
                login(email, password)
            }
        }

        binding.tvRegist.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrationActivity::class.java))
        }
    }

    private fun login(email: String, pass: String) {
        lifecycleScope.launch {
            try {
                loginViewModel.login(email, pass)
                loginViewModel.loginResult.observe(this@LoginActivity) {
                    it.msg?.let { msg -> showToast(msg) }

                    if (it.user?.accessToken != null) {
                        // menyimpan ke preference
                        val dataUser = UserModel(
                            it.user.accessToken
                        )
                        userPref.setUser(dataUser)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }

            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.msg

                e.printStackTrace()
                AlertDialog.Builder(this@LoginActivity).apply {
                    setTitle("Login Gagal!")
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