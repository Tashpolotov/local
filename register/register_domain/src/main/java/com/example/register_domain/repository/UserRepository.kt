package com.example.register_domain.repository

import com.example.core_utils.Resource
import com.example.register_domain.model.AccessModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(name: String): Flow<Resource<AccessModel>>
    suspend fun getPrefOnboarding(): Flow<Resource<Boolean>>
    suspend fun setPrefOnboarding()

    suspend fun setAccessTokenInShared(accessToken: AccessModel)
    suspend fun getAccessToken(): Flow<String?>


}