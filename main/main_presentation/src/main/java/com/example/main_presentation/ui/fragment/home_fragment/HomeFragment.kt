package com.example.main_presentation.ui.fragment.home_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.main_presentation.R
import com.example.main_presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var sharedPref: SharedPref

    override fun initialize() {
        sharedPref = SharedPref(requireContext())
        val accessToken = sharedPref.accessToken
        if (accessToken != null) {
            Log.d("Token123", "Access Token: $accessToken")
        } else {
            Log.d("Token123", "Access Token is not available.")
        }
    }

}