package com.dicoding.lawmate.ui.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.lawmate.api.response.MsgItem
import com.dicoding.lawmate.databinding.ConsultationItemBinding

class LawyerConsultationAdapter(private val consultationList: List<MsgItem>) :
    RecyclerView.Adapter<LawyerConsultationAdapter.ConsultationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationViewHolder {
        val binding =
            ConsultationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return consultationList.size
    }

    override fun onBindViewHolder(holder: ConsultationViewHolder, position: Int) {
        val consultation = consultationList[position]
        holder.bind(consultation)


    }

    class ConsultationViewHolder(private val binding: ConsultationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(consultation: MsgItem) {
            binding.tvDesc.text = consultation.description
            binding.tvJudul.text = consultation.title
            val tanggal = consultation.updatedAt?.substring(0, 10)
            binding.tvWaktu.text = tanggal

            itemView.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, DetailConsultationActivity::class.java)
                intent.putExtra("consultation_id", consultation.id)
                intent.putExtra("date", consultation.createdAt)
                intent.putExtra("desc", consultation.description)
                intent.putExtra("title", consultation.title)
                intent.putExtra("photo", consultation.pictureURI)
                intent.putExtra("user_id", consultation.userId)
                intent.putExtra("ekstrak", consultation.ekstrakteks)

                context.startActivity(intent)
            }
        }


    }
}
