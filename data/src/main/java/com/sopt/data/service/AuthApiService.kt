package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.dto.request.RequestRefreshTokenDto
import com.sopt.data.dto.response.ResponseRefreshTokenDto
import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.data.dto.response.ResponseSignUpDto
import com.sopt.data.dto.response.ResponseSocialLoginDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.AUTH
import com.sopt.data.service.ApiKeyStorage.AUTHORIZATION
import com.sopt.data.service.ApiKeyStorage.AUTHORIZE
import com.sopt.data.service.ApiKeyStorage.SIGN_IN
import com.sopt.data.service.ApiKeyStorage.SIGN_UP
import com.sopt.data.service.ApiKeyStorage.TOKEN_REISSUE
import com.sopt.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @POST("/$API/$V1/$AUTH/$AUTHORIZE")
    suspend fun postRefreshToken(
        @Body request: RequestRefreshTokenDto
    ): BaseResponse<ResponseRefreshTokenDto>

    @Multipart
    @POST("/$API/$V1/$AUTH/$SIGN_UP")
    suspend fun postSignUp(
        @Part("memberName") memberName: RequestBody,
        @Part memberProfileImage: MultipartBody.Part?,
        @Part("authType") authType: RequestBody,
        @Part("authId") authId: RequestBody
    ): BaseResponse<ResponseSignUpDto>
}
