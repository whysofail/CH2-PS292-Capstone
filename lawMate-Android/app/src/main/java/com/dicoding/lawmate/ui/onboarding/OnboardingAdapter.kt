package com.example.lawmateapps.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.lawmate.databinding.OnboardingItemListBinding

class OnboardingAdapter(private val onboardingItems: List<OnboardingItems>) :
    RecyclerView.Adapter<OnboardingAdapter.OnBoardingItemsViewHolder>() {
    private lateinit var onboardingItemListBinding: OnboardingItemListBinding

    class OnBoardingItemsViewHolder(private val binding: OnboardingItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboardingItems: OnboardingItems) {
            with(binding) {
                imgOnBoarding.setImageResource(onboardingItems.onBoardingImages)
                textTitle.text = onboardingItems.title
                textDiskripsi.text = onboardingItems.deskripsi
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemsViewHolder {
        val listOnBoardong = OnboardingItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnBoardingItemsViewHolder(
            listOnBoardong
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemsViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}