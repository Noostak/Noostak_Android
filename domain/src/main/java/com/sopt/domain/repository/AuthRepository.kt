package com.sopt.domain.repository

import com.sopt.domain.entity.AuthEntity
import com.sopt.domain.entity.AuthTypeEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {
    suspend fun postSocialLogin(
        token: String,
        request: AuthTypeEntity
    ): Result<AuthEntity>

    suspend fun postSignUp(
        accessToken: String,
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody
    ): Result<AuthEntity>
}
