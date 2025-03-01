package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody,
        authId: RequestBody
    ) = authRepository.postSignUp(memberName, memberProfileImage, authType, authId)
}
