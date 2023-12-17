package com.dicoding.lawmate.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.ActivityDetailArticleBinding
import com.google.android.material.appbar.MaterialToolbar

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil ID cerita dari intent
        val judul = intent.getStringExtra("judul")
        val desc = intent.getStringExtra("description")
        val date = intent.getStringExtra("date")
        val images = intent.getStringExtra("images")

        Glide.with(binding.root)
            .load(images)
            .into(binding.ivArticle)


        binding.tvJudul.text=judul
        binding.tvDesc.text=desc
        binding.tvDate.text=date

        val toolbar: MaterialToolbar = binding.topAppBarDetail

        // Mengatur aksi ketika ikon navigasi diklik
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Fungsi ini akan kembali ke halaman sebelumnya
        }
    }
}