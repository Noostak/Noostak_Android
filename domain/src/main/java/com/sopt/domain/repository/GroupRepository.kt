package com.sopt.domain.repository

import com.sopt.domain.entity.GroupEntity

interface GroupRepository {
    suspend fun getGroups(): Result<List<GroupEntity>>
    suspend fun postGroup(groupName: String, groupProfileImageUrl: String?): Result<String>
}
