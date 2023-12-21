package com.dicoding.lawmate.ui.lawyer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.bumptech.glide.Glide
import com.dicoding.lawmate.api.response.DataItem
import com.dicoding.lawmate.databinding.ModalLayoutBinding
import com.dicoding.lawmate.ui.consultation.ConsultationActivity

class ModalActivity(
    context: Context,
    private val lawyers: DataItem
) : Dialog(context) {

    private lateinit var binding: ModalLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ModalLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tags = lawyers.lawyerTags ?: emptyList()

        val tagNameList = mutableListOf<String?>()
        tags.forEach {
            tagNameList.add(it.name)
        }

        val tagDescriptionList = mutableListOf<String?>()
        tags.forEach {
            tagDescriptionList.add(it.description)
        }

        val tagName = tagNameList.joinToString(separator = ", ")
        val tagDescription = tagDescriptionList.joinToString(separator = ", ")

        binding.tvName.text = "${lawyers.firstName} ${lawyers.lastName}"
        binding.tvTag.text = tagName
        binding.tvDescTag.text = tagDescription
        binding.tvFee.text = "Rp"+lawyers.fee



        Glide.with(binding.root)
            .load(lawyers.profilePicture)
            .into(binding.ivLawyer)


        binding.ivClose.setOnClickListener{
            dismiss()
        }

        binding.btnLanjut.setOnClickListener{
            val intent = Intent(context, ConsultationActivity::class.java)
            intent.putExtra("desc", "")
            intent.putExtra("tag", "Umum")
            intent.putExtra("lawyer_id", lawyers.id)
            intent.putExtra("name_lawyers", "${lawyers.firstName} ${lawyers.lastName}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }
}