package com.example.register_presentation.ui.onboarding.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.Resource
import com.example.register_domain.usecase.PrefOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefOnboardingUseCase: PrefOnboardingUseCase
) : ViewModel() {

    private val _isOnboardingShowed = MutableStateFlow<Resource<Boolean>>(Resource.Empty())
    val isOnboardingShowed: StateFlow<Resource<Boolean>> = _isOnboardingShowed.asStateFlow()

    suspend fun getPrefOnboarding() {
        prefOnboardingUseCase.getPrefOnboarding().onEach {
            when (it) {
                is Resource.Empty -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data == null)
                        _isOnboardingShowed.value = Resource.Empty()
                    else
                        _isOnboardingShowed.value = Resource.Success(it.data)
                }

                is Resource.Error -> _isOnboardingShowed.value = Resource.Error(it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun setPrefOnboarding() {
        viewModelScope.launch {
            prefOnboardingUseCase.setPrefOnboarding()
            getPrefOnboarding()
        }
    }

}