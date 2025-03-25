package com.sopt.domain.repository

import com.sopt.domain.entity.ProfileEntity

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileEntity>
    suspend fun patchProfile(
        memberName: String,
        memberProfileImage: String?
    ): Result<Unit>
}
