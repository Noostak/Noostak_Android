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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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
        groupProfileImageUrl: String?
    ): Result<GroupSuccessEntity> {
        return runCatching {
            val textRequestBody = createContentRequestBody(groupName)
            val imagePart = contentResolver.createImagePart(groupProfileImageUrl, FILE_NAME)

            groupDataSource.postGroup(textRequestBody, imagePart).result?.toGroupSuccessEntity() ?: GroupSuccessEntity()
        }.onFailure { return it.handleThrowable() }
    }

    private fun createContentRequestBody(groupName: String): RequestBody {
        val contentJson = JSONObject().apply {
            put(GROUP_NAME, groupName)
        }.toString()
        return contentJson.toRequestBody("application/json".toMediaTypeOrNull())
    }

    companion object {
        private const val FILE_NAME = "groupProfileImageUrl"
        private const val GROUP_NAME = "groupName"
    }
}
