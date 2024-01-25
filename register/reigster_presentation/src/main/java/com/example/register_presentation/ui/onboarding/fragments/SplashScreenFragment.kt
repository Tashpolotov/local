package com.example.register_presentation.ui.onboarding.fragments

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    private val binding by viewBinding(FragmentSplashScreenBinding::bind)
    private lateinit var sharedPref: SharedPref

    override fun initSubscribers() {
        sharedPref = SharedPref(requireContext())
        val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)
        binding.imgEnot.startAnimation(slideInAnimation)
        binding.tvTitle.startAnimation(slideInAnimation)


        Handler(Looper.getMainLooper()).postDelayed({

            if (sharedPref.accessToken != null) {
                val uri = "example.feature://home".toUri()
                val request = NavDeepLinkRequest.Builder
                    .fromUri(uri)
                    .build()
                findNavController().navigate(request)
            } else {

                if (findNavController().currentDestination?.id == R.id.splashScreenFragment) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_registerFragment)
                }
            }
        }, 3000)


        Handler(Looper.getMainLooper()).postDelayed({
            if (onBoardIsFinished()) {

                if (findNavController().currentDestination?.id == R.id.splashScreenFragment) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_registerFragment)
                }
            } else {

                if (findNavController().currentDestination?.id == R.id.splashScreenFragment) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingFragment)
                }
            }
        }, 3000)
    }

    private fun onBoardIsFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("finished", false)
    }
}