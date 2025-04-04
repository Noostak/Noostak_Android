package com.sopt.presentation.auth.splash

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel<SplashSideEffect>() {
    fun getIsAutoLogin() {
        viewModelScope.launch {
            if (userInfoRepository.getIsAutoLogin().first()) {
                emitSideEffect(SplashSideEffect.NavigateToHome)
            } else {
                Timber.d("${userInfoRepository.getIsAutoLogin().first()}")
                emitSideEffect(SplashSideEffect.NavigateToLogin)
            }
        }
    }
}
