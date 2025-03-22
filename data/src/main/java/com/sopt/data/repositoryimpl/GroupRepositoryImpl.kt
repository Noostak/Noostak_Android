package com.sopt.data.repositoryimpl

import android.content.ContentResolver
import com.sopt.data.datasource.GroupDataSource
import com.sopt.data.mapper.toGroupEntity
import com.sopt.data.mapper.toGroupSuccessEntity
import com.sopt.data.util.createImagePart
import com.sopt.data.util.handleThrowable
import com.sopt.domain.entity.GroupEntity
import com.sopt.domain.entity.GroupSuccessEntity
import com.sopt.domain.repository.GroupRepository
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val groupDataSource: GroupDataSource
) : GroupRepository {
    override suspend fun getGroups(): Result<List<GroupEntity>> {
        return runCatching {
            groupDataSource.getGroups().result?.groups?.map { it.toGroupEntity() } ?: emptyList()
        }
    }

    override suspend fun postGroup(
        groupName: String,
        groupProfileImage: String?
    ): Result<GroupSuccessEntity> {
        return runCatching {
            val imagePart = contentResolver.createImagePart(groupProfileImage, FILE_NAME)

            groupDataSource.postGroup(
                groupName.toRequestBody(),
                imagePart
            ).result?.toGroupSuccessEntity() ?: GroupSuccessEntity()
        }.onFailure { return it.handleThrowable() }
    }

    companion object {
        private const val FILE_NAME = "groupProfileImage"
    }
}
