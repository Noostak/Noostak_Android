package com.sopt.presentation.groupDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.domain.entity.CompleteEntity
import com.sopt.domain.entity.GroupDetailEntity
import com.sopt.domain.entity.ProgressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects: MutableSharedFlow<GroupDetailSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<GroupDetailSideEffect> get() = _sideEffects.asSharedFlow()

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(GroupDetailSideEffect.NavigateUp)
        }
    }

    val tabs = immutableListOf("진행중", "완료")
    val mockGroupDetail = GroupDetailEntity(
        name = "누스탁",
        memberCount = 10,
        progress = listOf(
            ProgressEntity(
                id = 1,
                title = "1주차",
                date = "2021.10.01 ~ 2021.10.07",
                number = 3,
                total = 5
            ),
            ProgressEntity(
                id = 2,
                title = "2주차",
                date = "2021.10.08 ~ 2021.10.14",
                number = 2,
                total = 5
            ),
            ProgressEntity(
                id = 3,
                title = "3주차",
                date = "2021.10.15 ~ 2021.10.21",
                number = 0,
                total = 5
            ),
            ProgressEntity(
                id = 4,
                title = "4주차",
                date = "2021.10.22 ~ 2021.10.28",
                number = 0,
                total = 5
            )
        ),
        complete = listOf(
            CompleteEntity(
                id = 1,
                title = "5주차",
                date = "2021.10.29 ~ 2021.11.04",

            ),
            CompleteEntity(
                id = 2,
                title = "6주차",
                date = "2021.11.05 ~ 2021.11.11",

            )
        )
    )
}