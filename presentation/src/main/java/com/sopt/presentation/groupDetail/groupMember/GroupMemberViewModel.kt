package com.sopt.presentation.groupDetail.groupMember

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.GroupDetailInfoEntity
import com.sopt.domain.repository.GroupDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupMemberViewModel @Inject constructor(
    private val groupDetailInfoRepository: GroupDetailRepository
) : BaseViewModel<GroupMemberSideEffect>() {

    private val _groupMembersState = MutableStateFlow<UiState<GroupDetailInfoEntity>>(UiState.Empty)
    val groupMembersState: StateFlow<UiState<GroupDetailInfoEntity>> = _groupMembersState

    fun getGroupMembers(groupId: Long) {
        viewModelScope.launch {
            _groupMembersState.value = UiState.Loading
            groupDetailInfoRepository.getGroupInfoDetail(groupId)
                .onSuccess { entity ->
                    _groupMembersState.value = UiState.Success(entity)
                }.onFailure {
                    Timber.e(it)
                    _groupMembersState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun navigateUp() {
        emitSideEffect(GroupMemberSideEffect.NavigateUp)
    }
}

sealed class GroupMemberSideEffect {
    data object NavigateUp : GroupMemberSideEffect()
}
