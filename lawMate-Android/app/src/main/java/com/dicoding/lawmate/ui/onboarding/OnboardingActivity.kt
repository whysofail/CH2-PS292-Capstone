package com.dicoding.lawmate.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.ActivityOnboardingBinding
import com.dicoding.lawmate.ui.authentication.login.LoginActivity
import com.example.lawmateapps.onboarding.OnboardingAdapter
import com.example.lawmateapps.onboarding.OnboardingItems

class OnboardingActivity : AppCompatActivity() {
    private lateinit var onboardingBinding: ActivityOnboardingBinding
    private lateinit var onBoardingItemsAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(onboardingBinding.root)
        setOnBoardingItems()
        setupIndicator()
        setCurrentIndicator(0)
    }

    private fun setOnBoardingItems() {
        onBoardingItemsAdapter = OnboardingAdapter(
            listOf(
                OnboardingItems(
                    onBoardingImages = R.drawable.onboarding1,
                    title = "Bingung Dengan Hukum?",
                    deskripsi = "Kami akan memberikan solusi dari permasalahan anda"
                ),
                OnboardingItems(
                    onBoardingImages = R.drawable.onboarding2,
                    title = "Mau Update Tentang Hukum?",
                    deskripsi = "LawMate memberikan kabar hukum terupadate untuk anda"
                ),
                OnboardingItems(
                    onBoardingImages = R.drawable.onboarding3,
                    title = "Ingin Konsultasi?",
                    deskripsi = "Kami menyediakan lawyer yang siap menjawab permasalahan anda"
                )
            )
        )
        onboardingBinding.onboardingViewpager.adapter = onBoardingItemsAdapter
        onboardingBinding.onboardingViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingBinding.onboardingViewpager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER



        onboardingBinding.tvSkip.setOnClickListener {
            navigateToScreenOne()
        }


    }

    private fun navigateToScreenOne() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(onBoardingItemsAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParms.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bg_indicator_inactiv
                    )
                )
                it.layoutParams = layoutParms
                onboardingBinding.indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = onboardingBinding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = onboardingBinding.indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bg_indicator_activ
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bg_indicator_inactiv
                    )
                )
            }
        }
    }

}