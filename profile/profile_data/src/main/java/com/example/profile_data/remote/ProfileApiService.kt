package com.example.profile_data.remote

import com.example.profile_data.model.ProfileDataModel
import retrofit2.http.GET
import retrofit2.http.Header


interface ProfileApiService {

        @GET("/api/v1/profile/stats/")
        suspend fun getProfile(@Header("Authorization") token: String): ProfileDataModel
}
