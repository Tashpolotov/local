package com.example.register_data.di

import android.content.Context
import com.example.core_utils.SharedPref
import com.example.register_data.remote.UserApiService
import com.example.register_data.remote.UserRetrofitClient
import com.example.register_data.repository.UserRepositoryImpl
import com.example.register_domain.repository.UserRepository
import com.example.register_domain.usecase.GetAccessTokenUseCase
import com.example.register_domain.usecase.PrefOnboardingUseCase
import com.example.register_domain.usecase.SetAccessTokenInSharedUseCase
import com.example.register_domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RegisterDataModule {

    @Provides
    fun provideUserRetrofitClient() = UserRetrofitClient()

    @Provides
    fun provideRegisterUserApiService(userRetrofitClient: UserRetrofitClient) =
        userRetrofitClient.createApiService()

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) = SharedPref(context)

    @Provides
    fun provideRegisterUserRepository(
        apiService: UserApiService,
        sharedPref: SharedPref
    ): UserRepository {
        return UserRepositoryImpl(apiService, sharedPref)
    }

    @Provides
    fun providePrefOnboardingUseCase(userRepository: UserRepository) =
        PrefOnboardingUseCase(userRepository)

    @Provides
    fun provideSetAccessTokenUseCase(userRepository: UserRepository) =
        SetAccessTokenInSharedUseCase(userRepository)

    @Provides
    fun provideGetAccessToken(userRepository: UserRepository) =
        GetAccessTokenUseCase(userRepository)
}