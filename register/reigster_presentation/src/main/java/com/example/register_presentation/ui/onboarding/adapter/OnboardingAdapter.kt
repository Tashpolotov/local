package com.example.register_presentation.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.presentation.R
import com.example.presentation.databinding.OnboardingItemBinding
import com.example.register_domain.model.OnboardingModel

class OnboardingAdapter(
    private val btnNextPageClick: () -> Unit,
    private val btnStartClick: () -> Unit
) : Adapter<OnboardingViewHolder>() {

    private val onboardingData = listOf(
        OnboardingModel(
            "Весело учим ПДД!",
            "Здесь ты сможешь стать настоящим экспертом по ПДД, проходя увлекательные уроки. За каждый пройденный урок ты будешь получать  баллы, которые помогут тебе стать настоящим чемпионом дорог!",
            R.drawable.img_onboarding_first_page
        ),
        OnboardingModel(
            "Смотри и учись!",
            " У нас есть крутые обучающие видео, которые сделают из тебя настоящего профессионала в ПДД. Здесь ты найдешь множество интересных историй, советов и забавных моментов. Погружайся в мир дорожной безопасности вместе с яркими персонажами!",
            R.drawable.img_onboarding_second_page
        ),
        OnboardingModel(
            "Стань чемпионом ПДД!",
            "Проходи уроки, зарабатывай баллы и вступай в захватывающие соревнования. Покажи, насколько ты внимателен и ответственен на дороге! Стань лучшим и получи заслуженные награды. Дорога ждет своего нового чемпиона – может, это будешь именно ты?",
            R.drawable.img_onboarding_third_page
        ),
        OnboardingModel(
            "Шагом к Знаниям:",
            "Правила для Детей"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnboardingViewHolder(
        OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        btnStartClick = btnStartClick,
        btnNextPageClick = btnNextPageClick
    )

    override fun getItemCount() = onboardingData.size

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingData[position], itemCount, position)
    }
}