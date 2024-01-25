package com.example.profile_presenter.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core_utils.Resource
import com.example.core_utils.base.BaseViewModel
import com.example.profile_data.model.ProfileDataModel
import com.example.profile_domain.model.ProfileDomainModel
import com.example.profile_domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase:ProfileUseCase):BaseViewModel() {

    private val _profile = MutableStateFlow<Resource<ProfileDomainModel>>(Resource.Empty())
    val profile = _profile.asStateFlow()


    fun loadProfile(){
        viewModelScope.launch {
            useCase.getProfile().collectData(_profile)
        }
    }
}