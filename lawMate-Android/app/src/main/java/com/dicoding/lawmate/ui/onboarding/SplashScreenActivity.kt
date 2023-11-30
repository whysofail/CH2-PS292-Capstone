package com.dicoding.lawmate.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySplashScreenBinding
    companion object{
        const val SPLASH_TIME: Long = 5000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        setContentView(splashScreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}