package com.sopt.presentation.auth.signup

data class SignUpState(
    val userName: String = "",
    val profileImageUri: String? = null,
    val authId: String = "",
    val isPermissionGranted: Boolean = false,
    val isNameCheck: Boolean = false
)