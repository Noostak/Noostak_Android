package com.sopt.domain.entity

data class UserEntity(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val userId: Int? = null,
    val isAutoLogin: Boolean = false,
    val nickName: String = "",
    val profileImage: String? = null
)
