package com.dicoding.lawmate.ui.lawyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.lawmate.api.response.DataItem
import com.dicoding.lawmate.api.response.LawyerTagsItem
import com.dicoding.lawmate.databinding.LawyersItemBinding

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

            binding.tvName.text = "${lawyers.firstName} ${lawyers.lastName}"
            binding.tvTag.text = tagName
            binding.tvDescriptionTag.text = tagDescription

            Glide.with(binding.root)
                .load("https://www.iryanali.com/wp-content/uploads/2020/11/Hotman-Paris-3.jpg")
                .into(binding.ivLawyers)

        }

    }


}
