package com.example.register_presentation.ui.onboarding.fragments

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.Resource
import com.example.core_utils.base.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentOnboardingBinding
import com.example.register_presentation.ui.onboarding.adapter.OnboardingAdapter
import com.example.register_presentation.ui.onboarding.fragments.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private val adapter = OnboardingAdapter(
        btnNextPageClick = this::btnNextPageClick,
        btnStartClick = this::btnStartClick
    )
    private val viewModel by viewModels<OnboardingViewModel>()

    override fun onStart() {
        super.onStart()
        initViewPager()
        initFlow()
        lifecycleScope.launch {
            viewModel.getPrefOnboarding()
        }
    }

    private fun initFlow() {
        viewModel.isOnboardingShowed.onEach {
            when (it) {
                is Resource.Empty -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data!!) {
                        findNavController().navigate(R.id.action_onboardingFragment_to_registerFragment)
                    }
                }

                is Resource.Error -> Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }.launchIn(lifecycleScope)
    }

    private fun initViewPager() = with(binding) {
        viewpager.adapter = adapter
        dotsIndicator.attachTo(viewpager)
    }

    private fun btnNextPageClick() {
        binding.viewpager.currentItem = binding.viewpager.currentItem + 1
    }

    private fun btnStartClick() {
        viewModel.setPrefOnboarding()
    }

}