package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupsDto
import com.sopt.data.dto.response.ResponsePostGroupCodeDto
import com.sopt.data.dto.response.ResponsePostGroupDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.JOIN
import com.sopt.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GroupApiService {
    @GET("/$API/$V1/$GROUPS")
    suspend fun getGroups(): BaseResponse<ResponseGetGroupsDto>

    @Multipart
    @POST("/$API/$V1/$GROUPS")
    suspend fun postGroup(
        @Part("groupName") groupName: RequestBody,
        @Part groupProfileImage: MultipartBody.Part?
    ): BaseResponse<ResponsePostGroupDto>

    @POST("/$API/$V1/$GROUPS/$JOIN")
    suspend fun postGroupCode(@Body groupInviteCode: String): BaseResponse<ResponsePostGroupCodeDto>
}
