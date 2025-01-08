package com.sopt.presentation.auth.signup

data class SignUpState(
    val name: String = "",
    val profileImage: String? = null,
    val authType: String = "",
    val authId: String = ""
)