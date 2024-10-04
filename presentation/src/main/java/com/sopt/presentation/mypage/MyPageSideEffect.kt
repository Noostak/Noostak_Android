package com.sopt.presentation.mypage

sealed class MyPageSideEffect {
    data class NavigateToExample(val text: String) : MyPageSideEffect()
}