package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody
    ) = authRepository.postSignUp(accessToken, memberName, memberProfileImage, authType)
}
