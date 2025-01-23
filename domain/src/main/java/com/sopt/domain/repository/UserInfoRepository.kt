package com.sopt.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>
    fun getUserId(): Flow<Int>
    fun getIsAutoLogin(): Flow<Boolean>
    fun getNickname(): Flow<String>
    fun getProfileImage(): Flow<String>

    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveUserId(userId: Int)
    suspend fun saveIsAutoLogin(isAutoLogin: Boolean)
    suspend fun saveNickname(nickname: String)
    suspend fun saveProfileImage(profileImage: String)

    suspend fun clearAll()

    suspend fun clearForRefreshToken()
}
