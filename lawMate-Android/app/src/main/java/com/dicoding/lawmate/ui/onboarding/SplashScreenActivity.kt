package com.dicoding.lawmate.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.databinding.ActivitySplashScreenBinding
import com.dicoding.mystoryapp.preference.UserPref

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


        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        // Cek apakah pengguna sudah login
        if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
            // Redirect ke layar utama
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }, SPLASH_TIME)
        }


    }
}