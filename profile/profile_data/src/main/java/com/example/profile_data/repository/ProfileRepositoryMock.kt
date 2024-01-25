package com.example.profile_data.repository

import android.provider.ContactsContract
import com.example.core_utils.Resource
import com.example.core_utils.base.BaseRepository
import com.example.profile_data.model.ProfileDataModel
import com.example.profile_domain.model.ProfileDomainModel
import com.example.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
class ProfileRepositoryMock @Inject constructor(): BaseRepository(), ProfileRepository {


    override suspend fun getProfile(): Flow<Resource<ProfileDomainModel>> {

        return flow {
            val mockProfileData = ProfileDomainModel(
                1, "betmen", "Pewexod123465", "1",
                "Voditel smertnik'", "999456", "123", "489")

            emit(Resource.Success(mockProfileData))
        }

    }

}*/
