package com.sopt.domain.repository

import com.sopt.domain.entity.GroupEntity
import com.sopt.domain.entity.GroupSuccessEntity

interface GroupRepository {
    suspend fun getGroups(): Result<List<GroupEntity>>
    suspend fun postGroup(
        groupName: String,
        groupProfileImageUrl: String?
    ): Result<GroupSuccessEntity>
}
