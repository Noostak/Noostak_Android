package com.sopt.presentation.groupCreate.groupCreateSuccess.regex

fun generateRandomCode(): String {
    val charset = ('0'..'9') + ('A'..'Z')
    return List(6) { charset.random() }.joinToString("")
}
