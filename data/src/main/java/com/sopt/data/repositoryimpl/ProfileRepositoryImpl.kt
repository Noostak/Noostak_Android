package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.ProfileDataSource
import com.sopt.data.mapper.toProfileEntity
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<ProfileEntity> {
        return runCatching {
            profileDataSource.getProfile().result?.toProfileEntity() ?: ProfileEntity()
        }
    }
}
