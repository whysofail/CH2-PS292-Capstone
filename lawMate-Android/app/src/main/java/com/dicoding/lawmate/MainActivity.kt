package com.dicoding.lawmate

import HomeFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.lawmate.databinding.ActivityMainBinding
import com.dicoding.lawmate.ui.authentication.login.LoginActivity
import com.dicoding.lawmate.ui.chat.ChatFragment
import com.dicoding.lawmate.ui.lawbot.LawBotFragment
import com.dicoding.lawmate.ui.lawyer.LawyerFragment
import com.dicoding.lawmate.ui.profile.ProfileFragment
import com.dicoding.mystoryapp.preference.UserModel
import com.dicoding.mystoryapp.preference.UserPref

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPref: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the app bar menu item click listener
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    userPref = UserPref(this)
                    val dataUser = UserModel(null)
                    userPref.setUser(dataUser)

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }


        replaceFragment(HomeFragment())
        // Bottom Navigation
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_konsul -> replaceFragment(LawBotFragment())
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