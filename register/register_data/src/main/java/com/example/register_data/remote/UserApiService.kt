package com.example.register_data.remote

import com.example.core_utils.Resource
import com.example.register_domain.model.AccessModel
import com.example.register_domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("/api/v1/registration/")
    suspend fun userRegister(@Body userModel: UserModel):AccessModel
}