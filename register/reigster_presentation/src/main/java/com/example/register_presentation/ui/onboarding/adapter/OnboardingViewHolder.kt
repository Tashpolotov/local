package com.example.register_presentation.ui.onboarding.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.presentation.R
import com.example.presentation.databinding.OnboardingItemBinding
import com.example.register_domain.model.OnboardingModel

class OnboardingViewHolder(
    private val binding: OnboardingItemBinding,
    private val btnNextPageClick: () -> Unit,
    private val btnStartClick: () -> Unit
) : ViewHolder(binding.root) {

    fun bind(onboarding: OnboardingModel, count: Int, position: Int) =
        with(binding) {
            if (position == count - 1) {
                imgOnboardingLastPage.visibility = View.VISIBLE
                imgOnboarding.visibility = View.GONE
                onboardingContainer.setBackgroundResource(R.drawable.img_onboarding_last_page_background)
                btnNext.text = "Начать"
            } else {
                imgOnboardingLastPage.visibility = View.GONE
                imgOnboarding.visibility = View.VISIBLE
                onboardingContainer.setBackgroundResource(R.color.onboarding_background)
                btnNext.text = "Далее"
            }
            btnNext.setOnClickListener {
                if (btnNext.text == "Начать"){
                    btnStartClick()
                }else if (btnNext.text == "Далее"){
                    btnNextPageClick()
                }
            }
            tvOnboardingTitle.text = onboarding.title
            tvOnboardingDescription.text = onboarding.description
            onboarding.image?.let { imgOnboarding.setImageResource(it) }
        }

}