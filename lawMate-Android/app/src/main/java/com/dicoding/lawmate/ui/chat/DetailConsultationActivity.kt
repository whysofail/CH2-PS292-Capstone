package com.dicoding.lawmate.ui.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.lawmate.databinding.ActivityDetailConsultationBinding
import com.dicoding.mystoryapp.preference.UserPref
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class DetailConsultationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailConsultationBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var userPref:UserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPref = UserPref(this)

        viewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)

        binding = ActivityDetailConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val consultation_id = intent.getStringExtra("consultation_id")
        val date = intent.getStringExtra("date")
        val desc = intent.getStringExtra("desc")
        val title = intent.getStringExtra("title")
        val photo = intent.getStringExtra("photo")
        val user_id = intent.getStringExtra("user_id")
        val ekstrak = intent.getStringExtra("ekstrak")

        val tanggal = date?.substring(0, 10)

        lifecycleScope.launch {
            try {
                if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                    val token = userPref.getUser().accessToken.toString()
                    viewModel.getUserById(user_id!!, token)
                }
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@DetailConsultationActivity,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        var name:String? = null
        viewModel.user.observe(this){
            name = "${it.firstName} ${it.lastName}"
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
            if (!it){
                binding.tvName.text = "Nama : $name"
                binding.tvTitle.text = "Judul : $title"
                binding.tvDate.text = "Tanggal : $tanggal"
                binding.tvDesc.text = "Deskripsi : $desc"

                binding.tvTranskrip.text = "Ekstrak Gambar :\n$ekstrak"
                Glide.with(binding.root)
                    .load(photo)
                    .into(binding.ivCon)
            }
        }





        // Mendapatkan referensi ke toolbar dari layout
        val toolbar: MaterialToolbar = binding.topAppBarChat

        // Mengatur aksi ketika ikon navigasi diklik
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Fungsi ini akan kembali ke halaman sebelumnya
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}