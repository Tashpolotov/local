package com.example.profile_data.repository

import android.util.Log
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseRepository
import com.example.profile_data.mapper.toProfile
import com.example.profile_data.remote.ProfileApiService
import com.example.profile_domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val apiService: ProfileApiService,
    private val sharedPref: SharedPref):
    BaseRepository(), ProfileRepository {
    override suspend fun getProfile() = doRequest {
        val accessToken = sharedPref.accessToken?.toString()
        Log.d("ProfileRepository", "Access Token: $accessToken")

        try {
            apiService.getProfile(accessToken.toString()).toProfile()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}", e)
            throw e
        }
    }
}
