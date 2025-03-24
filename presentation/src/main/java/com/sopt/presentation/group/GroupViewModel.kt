package com.sopt.presentation.group

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.GroupEntity
import com.sopt.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupSideEffect>() {
    private val _showFABDialog = MutableStateFlow(false)
    val showFABDialog: StateFlow<Boolean> get() = _showFABDialog

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    private val _getGroupsState: MutableStateFlow<UiState<List<GroupEntity>>> =
        MutableStateFlow(UiState.Empty)
    val getGroupsState: StateFlow<UiState<List<GroupEntity>>> get() = _getGroupsState.asStateFlow()

    fun getGroups() {
        viewModelScope.launch {
            _getGroupsState.emit(UiState.Loading)
            groupRepository.getGroups().onSuccess { _getGroupsState.emit(UiState.Success(it)) }
                .onFailure {
                    _getGroupsState.emit(UiState.Failure(it.message.toString()))
                    triggerErrorDialog()
                }
        }
    }

    fun showErrorDialog(show: Boolean) {
        _showErrorDialog.update { show }
    }

    private fun triggerErrorDialog() {
        emitSideEffect(GroupSideEffect.ShowErrorDialog)
    }

    fun showFABDialog(show: Boolean) {
        _showFABDialog.update { show }
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(GroupSideEffect.NavigateToGroupDetail(groupId))
    }

    fun navigateToGroupCreate() {
        emitSideEffect(GroupSideEffect.NavigateToGroupCreate)
    }

    fun navigateToGroupEnter() {
        emitSideEffect(GroupSideEffect.NavigateToGroupEnter)
    }
}
