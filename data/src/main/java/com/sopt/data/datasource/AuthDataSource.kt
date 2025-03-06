package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.response.ResponsePostReissueTokenDto
import com.sopt.data.dto.response.ResponsePostSignUpDto
import com.sopt.data.dto.response.ResponsePostSocialLoginDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun postSocialLogin(
        token: String,
        request: RequestPostSocialLoginDto
    ): BaseResponse<ResponsePostSocialLoginDto>

    suspend fun postReissueToken(
        refreshToken: String
    ): BaseResponse<ResponsePostReissueTokenDto>

    suspend fun postSignUp(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?,
        authType: RequestBody,
        authId: RequestBody
    ): BaseResponse<ResponsePostSignUpDto>

    suspend fun postLogout(accessToken: String): BaseResponse<Unit>

    suspend fun deleteWithdraw(accessToken: String): BaseResponse<Unit>
}
