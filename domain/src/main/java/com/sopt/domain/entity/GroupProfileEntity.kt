package com.sopt.domain.entity

data class GroupProfileEntity(
    val groupName: String = "",
    val selectedImageUri: String? = null,
    val isPermissionGranted: Boolean = false,
    val isGroupNameCheck: Boolean = false
)
