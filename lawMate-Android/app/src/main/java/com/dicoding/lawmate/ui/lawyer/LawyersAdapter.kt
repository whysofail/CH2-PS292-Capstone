package com.dicoding.lawmate.ui.lawyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.lawmate.api.response.DataItem
import com.dicoding.lawmate.api.response.LawyerTagsItem
import com.dicoding.lawmate.api.response.MsgItem
import com.dicoding.lawmate.databinding.LawyersItemBinding
import com.dicoding.lawmate.ui.lawbot.ModalActivity
import java.text.NumberFormat
import java.util.Locale

class LawyersAdapter(private val lawyers: List<DataItem>) :
    RecyclerView.Adapter<LawyersAdapter.LawyersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawyersViewHolder {
        val binding = LawyersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawyersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lawyers.size
    }

    override fun onBindViewHolder(holder: LawyersViewHolder, position: Int) {
        val lawyers = lawyers[position]
        holder.bind(lawyers)
    }

    class LawyersViewHolder(private val binding: LawyersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lawyers: DataItem) {

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

            val fee = lawyers.fee?.toDouble()


                val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                val formattedAmount = format.format(fee)

                binding.tvName.text = "${lawyers.firstName} ${lawyers.lastName}"
                binding.tvTag.text = tagName
                binding.tvDescriptionTag.text = tagDescription
                binding.tvFee.text = formattedAmount

            binding.btnKonsultasikan.setOnClickListener{
                val modal = ModalActivity(binding.root.context, lawyers)
                modal.show()
            }

            Glide.with(binding.root)
                .load(lawyers.profilePicture)
                .into(binding.ivLawyers)

        }

    }


}
