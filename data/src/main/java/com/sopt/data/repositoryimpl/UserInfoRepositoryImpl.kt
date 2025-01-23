package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.UserDataSource
import com.sopt.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val authDataSource: UserDataSource
) : UserInfoRepository {
    override fun getAccessToken(): Flow<String> = authDataSource.accessToken

    override fun getRefreshToken(): Flow<String> = authDataSource.refreshToken

    override fun getUserId(): Flow<Int> = authDataSource.userId

    override fun getIsAutoLogin(): Flow<Boolean> = authDataSource.isAutoLogin

    override fun getNickname(): Flow<String> = authDataSource.nickname

    override fun getProfileImage(): Flow<String> = authDataSource.profileImage

    override suspend fun saveAccessToken(accessToken: String) {
        authDataSource.updateAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        authDataSource.updateRefreshToken(refreshToken)
    }

    override suspend fun saveUserId(userId: Int) {
        authDataSource.updateUserId(userId)
    }

    override suspend fun saveIsAutoLogin(isAutoLogin: Boolean) {
        authDataSource.updateIsAutoLogin(isAutoLogin)
    }

    override suspend fun saveNickname(nickname: String) {
        authDataSource.updateNickname(nickname)
    }

    override suspend fun saveProfileImage(profileImage: String) {
        authDataSource.updateProfileImage(profileImage)
    }

    override suspend fun clearAll() {
        authDataSource.clear()
    }

    override suspend fun clearForRefreshToken() {
        authDataSource.clearForRefreshToken()
    }
}
