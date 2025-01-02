package com.sopt.presentation.auth.signup
data class SignUpState(
    val name: String = "",
    val profileImage: String = "basic",
    val isButtonValid: Boolean = false,
    val authId: String = "",
    val showBottomSheet: Boolean = false
)