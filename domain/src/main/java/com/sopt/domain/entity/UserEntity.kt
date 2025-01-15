package com.sopt.domain.entity

data class UserEntity(
    val accessToken: String?,
    val refreshToken: String?,
    val userId: Int?,
    val isAutoLogin: Boolean,
    val nickName: String,
    val profileImage: String?
)
