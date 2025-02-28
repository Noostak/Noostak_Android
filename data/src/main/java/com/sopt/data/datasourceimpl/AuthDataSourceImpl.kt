package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.data.dto.response.ResponseSocialLoginDto
import com.sopt.data.service.AuthApiService
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
}
