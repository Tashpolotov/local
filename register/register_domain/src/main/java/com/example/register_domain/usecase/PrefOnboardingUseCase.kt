package com.example.register_domain.usecase

import com.example.register_domain.repository.UserRepository
import javax.inject.Inject

class PrefOnboardingUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun getPrefOnboarding() = userRepository.getPrefOnboarding()
    suspend fun setPrefOnboarding() = userRepository.setPrefOnboarding()

}
