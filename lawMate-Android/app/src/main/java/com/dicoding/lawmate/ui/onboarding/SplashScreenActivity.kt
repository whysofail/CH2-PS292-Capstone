package com.dicoding.lawmate.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.lawmate.LawyerMainActivity
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.databinding.ActivitySplashScreenBinding
import com.dicoding.lawmate.ui.profile.ProfileViewModel
import com.dicoding.mystoryapp.preference.UserPref
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySplashScreenBinding

    companion object {
        const val SPLASH_TIME: Long = 5000
    }

    private lateinit var userPref: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        setContentView(splashScreenBinding.root)

        var viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        // Cek apakah pengguna sudah login
        if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
            lifecycleScope.launch {
                val token = userPref.getUser().accessToken
                token?.let { viewModel.getUser(it) }
            }

            viewModel.user.observe(this){
                val id = it.roleId
                if (id == 1){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(applicationContext, LawyerMainActivity::class.java))
                    finish()
                }
            }

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }, SPLASH_TIME)
        }


    }
}