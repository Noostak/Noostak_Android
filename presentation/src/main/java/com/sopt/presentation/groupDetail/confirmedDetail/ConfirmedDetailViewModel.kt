package com.sopt.presentation.groupDetail.confirmedDetail

import com.sopt.core.util.BaseViewModel
import com.sopt.core.util.RearrangeList
import com.sopt.domain.entity.ConfirmedDetailEntity
import com.sopt.domain.entity.IdentityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmedDetailViewModel @Inject constructor() : BaseViewModel<ConfirmedDetailSideEffect>() {
    fun navigateUp() {
        emitSideEffect(ConfirmedDetailSideEffect.NavigateUp)
    }

    val mockConfirmedDetail = ConfirmedDetailEntity(
        myIdentity = IdentityEntity(
            availability = "available",
            position = 0,
            name = "이가을"
        ),
        date = "2025-01-06T00:00:00",
        startTime = "2025-01-06T11:00:00",
        endTime = "2025-01-06T14:00:00",
        category = "기타",
        likes = 15,
        liked = true,
        availableMembersCount = 5,
        availableMembers = listOf(
            "이가을", "대한민국만세", "최영희", "정영수",
            "선우정아", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf("류선재", "한강", "이영희", "박영수", "최영희", "정영수", "김태성",
            "백인혁", "임솔", "박복순", "정말자", "임금", "이현주", "류근덕", "김송이", "왕서희", "김선정", "김소희",
            "이자민", "박민서", "오연서", "이명진", "권장순", "문채영", "누스탁", "스케툭", "우주대스타", "동해물과", "백두산이","마르고닳도록",
                    "하느님이보우하사","우리나라만세","만세", "가을하늘공활한데","이기상과","이맘으로충성을","다하여","괴로우나즐거우나","나라",
            "사랑하세","무궁화삼천리","화려강산","대한사람","대한으로","길이보전하세","동해물과","백두산이","마르고닳도록","하느님이보우하사","우리나라만세",
            "나우솝트", "불닭좋아", "족발먹고싶어", "아개졸려", "마이멜로디", "쿠로미", "산리오캐릭터")
    )
}

sealed class ConfirmedDetailSideEffect {
    data object NavigateUp : ConfirmedDetailSideEffect()
}
