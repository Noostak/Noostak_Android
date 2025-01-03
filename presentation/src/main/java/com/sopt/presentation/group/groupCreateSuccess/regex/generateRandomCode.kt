package com.sopt.presentation.group.groupCreateSuccess.regex

fun generateRandomCode(): String {
    val charset = ('0'..'9') + ('A'..'Z')
    return List(6) { charset.random() }.joinToString("")
}