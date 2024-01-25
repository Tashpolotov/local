package com.example.register_domain.usecase

import com.example.register_domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun user(username:String) = repository.getUser(username)
}