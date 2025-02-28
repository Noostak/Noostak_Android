package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.request.RequestRefreshTokenDto
import com.sopt.data.dto.response.ResponseRefreshTokenDto
import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.data.dto.response.ResponseSignUpDto
import com.sopt.data.dto.response.ResponseSocialLoginDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun postSocialLogin(
        token: String,
        request: RequestPostSocialLoginDto
    ): BaseResponse<ResponseSocialLoginDto>

    suspend fun postReissueToken(
        refreshToken: String
    ): BaseResponse<ResponseReissueTokenDto>

    suspend fun postRefreshToken(
        request: RequestRefreshTokenDto
    ): BaseResponse<ResponseRefreshTokenDto>

    suspend fun postSignUp(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody,
        authId: RequestBody
    ): BaseResponse<ResponseSignUpDto>

    suspend fun postLogout(accessToken: String): BaseResponse<Unit>

    suspend fun deleteWithdraw(accessToken: String): BaseResponse<Unit>
}
