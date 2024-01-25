package com.example.profile_domain.usecase

import com.example.profile_domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ProfileRepository) {

    suspend fun getProfile() = repository.getProfile()

}