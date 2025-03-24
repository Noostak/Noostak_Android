package com.sopt.presentation.groupEnter

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.repository.GroupRepository
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class GroupEnterViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupEnterSideEffect>() {
    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    private val _postGroupEnterState: MutableStateFlow<UiState<Long>> =
        MutableStateFlow(UiState.Empty)
    val postGroupEnterState: StateFlow<UiState<Long>> get() = _postGroupEnterState.asStateFlow()

    fun postGroupCode(groupInviteCode: String) {
        viewModelScope.launch {
            _postGroupEnterState.emit(UiState.Loading)
            groupRepository.postGroupCode(groupInviteCode)
                .onSuccess { _postGroupEnterState.emit(UiState.Success(it)) }
                .onFailure { throwable ->
                    when (throwable) {
                        is IOException -> {
                            _postGroupEnterState.emit(UiState.Failure(throwable.message.toString()))
                            triggerErrorDialog()
                        }

                        else -> {
                            _postGroupEnterState.emit(UiState.Failure(throwable.message.toString()))
                            triggerFailureSnackBar()
                        }
                    }
                }
        }
    }

    private fun triggerFailureSnackBar() {
        emitSideEffect(GroupEnterSideEffect.ShowSnackBar(R.string.sb_group_enter_non_exist_code))
    }

    fun showErrorDialog(show: Boolean) {
        _showErrorDialog.update { show }
    }

    private fun triggerErrorDialog() {
        emitSideEffect(GroupEnterSideEffect.ShowErrorDialog)
    }

    fun navigateUp() {
        emitSideEffect(GroupEnterSideEffect.NavigateUp)
    }

    fun navigateToGroup() {
        emitSideEffect(GroupEnterSideEffect.NavigateToGroup)
    }
}
