package com.sopt.data.repositoryimpl

import android.content.ContentResolver
import com.sopt.data.datasource.ProfileDataSource
import com.sopt.data.mapper.toProfileEntity
import com.sopt.data.util.createImagePart
import com.sopt.data.util.handleThrowable
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.ProfileRepository
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<ProfileEntity> {
        return runCatching {
            profileDataSource.getProfile().result?.toProfileEntity() ?: ProfileEntity()
        }
    }

    override suspend fun patchProfile(
        memberName: String,
        memberProfileImage: String?
    ): Result<Unit> {
        return runCatching {
            val imagePart = contentResolver.createImagePart(memberProfileImage, FILE_NAME)

            profileDataSource.patchProfile(memberName.toRequestBody(), imagePart).result
            Unit
        }.onFailure { return it.handleThrowable() }
    }

    companion object {
        private const val FILE_NAME = "memberProfileImage"
    }
}
