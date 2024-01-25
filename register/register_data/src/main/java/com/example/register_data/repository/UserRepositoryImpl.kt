    package com.example.register_data.repository

    import android.util.Log
    import com.example.core_utils.Resource
    import com.example.core_utils.SharedPref
    import com.example.core_utils.base.BaseRepository
    import com.example.register_data.remote.UserApiService
    import com.example.register_domain.model.AccessModel
    import com.example.register_domain.model.UserModel
    import com.example.register_domain.repository.UserRepository
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.flow
    import retrofit2.HttpException
    import java.io.IOException
    import javax.inject.Inject

    class UserRepositoryImpl @Inject constructor(
        private val apiService: UserApiService,
        private val sharedPref: SharedPref
    ) : BaseRepository(), UserRepository {


        override suspend fun getUser(username: String): Flow<Resource<AccessModel>> {
            return doRequest {
                apiService.userRegister(UserModel(username))
            }
        }

        override suspend fun getPrefOnboarding() = flow {
            emit(Resource.Loading())

            try {
                emit(Resource.Success(sharedPref.isOnBoardingShowed))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Ой, что-то пошло не так"))
            }
        }

        override suspend fun setPrefOnboarding() {
            sharedPref.isOnBoardingShowed = true
        }

        override suspend fun setAccessTokenInShared(accessToken: AccessModel) {
            // Логирование токена перед сохранением
            Log.d("UserRepository", "Setting Access Token: $accessToken")
            sharedPref.accessToken = accessToken.access
            sharedPref.refreshToken = accessToken.refresh
        }

        override suspend fun getAccessToken() = flow {
            if (sharedPref.accessToken != null) {
                // Логирование токена при его получении
                Log.d("UserRepository", "Getting Access Token: ${sharedPref.accessToken}")
                emit(sharedPref.accessToken)
            } else {
                emit(null)
            }
        }
    }