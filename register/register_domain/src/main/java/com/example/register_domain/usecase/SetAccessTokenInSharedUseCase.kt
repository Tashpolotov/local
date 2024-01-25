package com.example.register_domain.usecase

import com.example.register_domain.model.AccessModel
import com.example.register_domain.repository.UserRepository
import javax.inject.Inject

class SetAccessTokenInSharedUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(accessToken: AccessModel) {
        userRepository.setAccessTokenInShared(accessToken)
    }
}
