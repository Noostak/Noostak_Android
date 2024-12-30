package com.sopt.presentation.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects = MutableSharedFlow<GroupSideEffect>()
    val sideEffects: MutableSharedFlow<GroupSideEffect> get() = _sideEffects

    fun navigateToGroupDetail(id: Long) {
        viewModelScope.launch {
            _sideEffects.emit(GroupSideEffect.NavigateToGroupDetail(id))
        }
    }
}
