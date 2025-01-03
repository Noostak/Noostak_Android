package com.sopt.presentation.auth.signup
data class SignUpState(
    val name: String = "",
    val profileImage: String? = "",
    val authType: String = "",
    val authId: String = ""
)