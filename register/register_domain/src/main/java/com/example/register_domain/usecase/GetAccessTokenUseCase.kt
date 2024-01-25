package com.example.register_domain.usecase

import com.example.register_domain.repository.UserRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute() = userRepository.getAccessToken()

}
