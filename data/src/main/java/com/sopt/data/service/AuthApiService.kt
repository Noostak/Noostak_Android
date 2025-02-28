package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.data.dto.response.ResponseSocialLoginDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.AUTH
import com.sopt.data.service.ApiKeyStorage.AUTHORIZATION
import com.sopt.data.service.ApiKeyStorage.SIGN_IN
import com.sopt.data.service.ApiKeyStorage.TOKEN_REISSUE
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("/$API/$V1/$AUTH/$SIGN_IN")
    suspend fun postSocialLogin(
        @Header(AUTHORIZATION) token: String,
        @Body requestPostSocialLoginDto: RequestPostSocialLoginDto
    ): BaseResponse<ResponseSocialLoginDto>

    @POST("/$API/$V1/$AUTH/$TOKEN_REISSUE")
    suspend fun postReissueToken(
        @Header(AUTHORIZATION) refreshToken: String
    ): BaseResponse<ResponseReissueTokenDto>
}
