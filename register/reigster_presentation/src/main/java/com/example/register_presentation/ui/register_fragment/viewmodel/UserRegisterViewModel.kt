package com.example.register_presentation.ui.register_fragment.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core_utils.Resource
import com.example.core_utils.base.BaseViewModel
import com.example.register_domain.model.AccessModel
import com.example.register_domain.usecase.GetAccessTokenUseCase
import com.example.register_domain.usecase.SetAccessTokenInSharedUseCase


import com.example.register_domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegisterViewModel @Inject constructor(
    private val useCase: UserUseCase,
    private val setAccessTokenInSharedUseCase: SetAccessTokenInSharedUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : BaseViewModel() {

    private val _registerUser = MutableStateFlow<Resource<AccessModel>>(Resource.Empty())
    val registerUser = _registerUser.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    fun loadUser(name: String) {
        viewModelScope.launch {
            useCase.user(name).collectData(_registerUser)
        }
    }

    fun getAccessToken() {
        viewModelScope.launch {
            getAccessTokenUseCase.execute().onEach {
                _accessToken.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun setAccessTokenInShared(accessToken: AccessModel) {
        viewModelScope.launch {
            setAccessTokenInSharedUseCase.execute(accessToken)
            getAccessToken()
        }
    }
}