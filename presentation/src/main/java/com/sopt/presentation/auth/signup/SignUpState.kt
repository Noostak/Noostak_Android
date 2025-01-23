package com.sopt.presentation.auth.signup

data class SignUpState(
    val nickname: String = "",
    val profileImageUri: String? = null,
    val authId: String = "",
    val isPermissionGranted: Boolean = false,
    val isNameCheck: Boolean = false
)
