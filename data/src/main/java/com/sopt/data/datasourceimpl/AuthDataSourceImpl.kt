package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.response.ResponsePostReissueTokenDto
import com.sopt.data.dto.response.ResponsePostSignUpDto
import com.sopt.data.dto.response.ResponsePostSocialLoginDto
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
    ): BaseResponse<ResponsePostSocialLoginDto> {
        return authApiService.postSocialLogin(token, request)
    }

    override suspend fun postReissueToken(
        refreshToken: String
    ): BaseResponse<ResponsePostReissueTokenDto> {
        return authApiService.postReissueToken(refreshToken)
    }

    override suspend fun postSignUp(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody,
        authId: RequestBody
    ): BaseResponse<ResponsePostSignUpDto> {
        return authApiService.postSignUp(memberName, memberProfileImage, authType, authId)
    }

    override suspend fun postLogout(accessToken: String): BaseResponse<Unit> {
        return authApiService.postLogout(accessToken)
    }

    override suspend fun deleteWithdraw(accessToken: String): BaseResponse<Unit> {
        return authApiService.deleteWithdraw(accessToken)
    }
}
