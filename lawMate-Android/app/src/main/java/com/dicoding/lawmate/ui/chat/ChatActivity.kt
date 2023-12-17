package com.dicoding.lawmate.ui.chat

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.ActivityChatBinding
import com.google.android.material.appbar.MaterialToolbar

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val judul = intent.getStringExtra("name")

        // Mendapatkan referensi ke toolbar dari layout
        val toolbar: MaterialToolbar = binding.topAppBarChat

        toolbar.setTitle(judul)

        // Mengatur aksi ketika ikon navigasi diklik
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Fungsi ini akan kembali ke halaman sebelumnya
        }

    }


}