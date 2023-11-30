package com.dicoding.lawmate.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.ActivityLoginBinding
import com.dicoding.lawmate.ui.authentication.registration.RegistrationActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

                loginViewModel.isLoading.observe(this){
                    showLoading(it)
                }

                lifecycleScope.launch {
                    loginViewModel.login(email, password)
                }

                loginViewModel.loginResult.observe(this){
                    showToast(it.name)
                }

            }

        }

        binding.tvRegist.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrationActivity::class.java))
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
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.visibility = View.GONE
        }
    }
}