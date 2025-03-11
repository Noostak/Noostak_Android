package com.sopt.domain.entity

data class AuthEntity(
    val accessToken: String,
    val refreshToken: String,
    val memberId: Int,
    val authType: String
)

data class AuthTypeEntity(
    val authType: String
)
