package com.example.profile_domain.repository

import com.example.core_utils.Resource
import com.example.profile_domain.model.ProfileDomainModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<Resource<ProfileDomainModel>>

}