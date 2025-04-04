package com.sopt.domain.entity

data class ProfileEntity(
    val memberName: String = "",
    val memberProfileImage: String? = null,
    val isPermissionGranted: Boolean = false,
    val isMemberNameCheck: Boolean = false
)
