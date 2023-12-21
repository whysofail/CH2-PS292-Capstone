package com.dicoding.lawmate.ui.lawbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.lawmate.api.response.DataItem
import com.dicoding.lawmate.databinding.LawyersItemBinding

class RekomendasiLawyersAdapter(
    private val lawyers: List<DataItem>,
    private val tag: String,
    private val desc: String
) :
    RecyclerView.Adapter<RekomendasiLawyersAdapter.LawyersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawyersViewHolder {
        val binding = LawyersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawyersViewHolder(binding, tag, desc)
    }

    override fun getItemCount(): Int {
        return lawyers.size
    }

    override fun onBindViewHolder(holder: LawyersViewHolder, position: Int) {
        val lawyers = lawyers[position]
        holder.bind(lawyers)
    }

    class LawyersViewHolder(
        private val binding: LawyersItemBinding,
        private val tag: String,
        private val desc: String
    ) :
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

            binding.tvName.text = "${lawyers.firstName} ${lawyers.lastName}"
            binding.tvTag.text = tagName
            binding.tvDescriptionTag.text = tagDescription
            binding.tvFee.text = "Rp" + lawyers.fee

            binding.btnKonsultasikan.setOnClickListener {
                val modal = ModalActivity(binding.root.context, tag, desc, lawyers)
                modal.show()
            }

            Glide.with(binding.root)
                .load(lawyers.profilePicture)
                .into(binding.ivLawyers)

        }

    }


}
