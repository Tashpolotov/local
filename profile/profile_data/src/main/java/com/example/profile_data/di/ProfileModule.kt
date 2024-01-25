package com.example.profile_data.di

import com.example.core_utils.SharedPref
import com.example.profile_data.remote.ProfileApiService
import com.example.profile_data.repository.ProfileRepositoryImpl
import com.example.profile_domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    //Api SErvise dobavit'


    @Provides
    fun provideProfileApiService(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(apiService: ProfileApiService, sharedPref: SharedPref):ProfileRepository{
        return ProfileRepositoryImpl(apiService, sharedPref)
    }
}