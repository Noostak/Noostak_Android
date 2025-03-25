package com.sopt.presentation.groupDetail.groupMember

import androidx.lifecycle.viewModelScope
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

    private val _groupMembers = MutableStateFlow<GroupDetailInfoEntity?>(null)
    val groupMembers: StateFlow<GroupDetailInfoEntity?> = _groupMembers

    fun navigateUp() {
        emitSideEffect(GroupMemberSideEffect.NavigateUp)
    }

    fun getGroupMembers(groupId: Long) {
        viewModelScope.launch {
            groupDetailInfoRepository.getGroupInfoDetail(groupId)
                .onSuccess { entity ->
                    _groupMembers.value = entity
                }.onFailure {
                    Timber.e(it)
                }
        }
    }
}

sealed class GroupMemberSideEffect {
    data object NavigateUp : GroupMemberSideEffect()
}
