package com.dicoding.lawmate

import HomeFragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.lawmate.databinding.ActivityMainBinding
import com.dicoding.lawmate.ui.authentication.login.LoginActivity
import com.dicoding.lawmate.ui.chat.ChatFragment
import com.dicoding.lawmate.ui.lawyer.LawyerFragment
import com.dicoding.lawmate.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        // Bottom Navigation
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
                R.id.navigation_chat -> replaceFragment(ChatFragment())
                R.id.navigation_lawyer -> replaceFragment(LawyerFragment())
                else -> {}
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}