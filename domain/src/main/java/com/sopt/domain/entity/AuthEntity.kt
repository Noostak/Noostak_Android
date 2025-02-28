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

data class ReissueTokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val authType: String
)

data class RefreshTokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val authId: String,
    val authType: String,
    val isMember: Boolean
)
