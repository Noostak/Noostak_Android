package com.sopt.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    val userId: Flow<Int>
    val isAutoLogin: Flow<Boolean>
    val nickname: Flow<String>
    val profileImage: Flow<String>

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateRefreshToken(refreshToken: String)

    suspend fun updateUserId(userId: Int)

    suspend fun updateIsAutoLogin(isAutoLogin: Boolean)

    suspend fun updateNickname(nickname: String)

    suspend fun updateProfileImage(profileImage: String)

    suspend fun clear()

    suspend fun clearForRefreshToken()
}
