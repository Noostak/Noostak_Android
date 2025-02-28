package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.request.RequestRefreshTokenDto
import com.sopt.data.dto.response.ResponseRefreshTokenDto
import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.data.dto.response.ResponseSignUpDto
import com.sopt.data.dto.response.ResponseSocialLoginDto
import com.sopt.data.service.AuthApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthDataSource {

    override suspend fun postSocialLogin(
        token: String,
        request: RequestPostSocialLoginDto
    ): BaseResponse<ResponseSocialLoginDto> {
        return authApiService.postSocialLogin(token, request)
    }

    override suspend fun postReissueToken(
        refreshToken: String
    ): BaseResponse<ResponseReissueTokenDto> {
        return authApiService.postReissueToken(refreshToken)
    }

    override suspend fun postRefreshToken(
        request: RequestRefreshTokenDto
    ): BaseResponse<ResponseRefreshTokenDto> {
        return authApiService.postRefreshToken(request)
    }

    override suspend fun postSignUp(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody,
        authId: RequestBody
    ): BaseResponse<ResponseSignUpDto> {
        return authApiService.postSignUp(memberName, memberProfileImage, authType, authId)
    }

    override suspend fun postLogout(accessToken: String): BaseResponse<Unit> {
        return authApiService.postLogout(accessToken)
    }

    override suspend fun deleteWithdraw(accessToken: String): BaseResponse<Unit> {
        return authApiService.deleteWithdraw(accessToken)
    }
}
