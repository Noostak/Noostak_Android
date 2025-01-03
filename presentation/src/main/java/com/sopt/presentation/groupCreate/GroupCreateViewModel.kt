package com.sopt.presentation.groupCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.domain.entity.GroupProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupCreateViewModel @Inject constructor() : ViewModel() {
    private val _sideEffect = MutableSharedFlow<GroupCreateSideEffect>()
    val sideEffect: SharedFlow<GroupCreateSideEffect> = _sideEffect.asSharedFlow()

    private val _groupProfileState = MutableStateFlow(GroupProfileEntity())
    val groupProfileState: StateFlow<GroupProfileEntity> = _groupProfileState

    fun updateGalleryPermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            _groupProfileState.update { it.copy(isPermissionGranted = isGranted) }
        }
    }

    fun navigateToGroupCreateSuccess() {
        viewModelScope.launch {
            _sideEffect.emit(GroupCreateSideEffect.NavigateToGroupCreateSuccess)
        }
    }

    fun requestGalleryPicker() {
        viewModelScope.launch {
            if (_groupProfileState.value.isPermissionGranted) _sideEffect.emit(GroupCreateSideEffect.RequestImagePicker)
            else _sideEffect.emit(GroupCreateSideEffect.ShowPermissionDeniedDialog)
        }
    }

    fun onImageSelected(imageUri: String?) {
        viewModelScope.launch {
            _groupProfileState.update { it.copy(selectedImageUri = imageUri) }
        }
    }

    fun onGroupNameChanged(groupName: String) {
        _groupProfileState.update { it.copy(groupName = groupName) }
        validateGroupName(groupName)
    }

    private fun validateGroupName(groupName: String) {
        viewModelScope.launch {
            _groupProfileState.update { it.copy(isGroupNameCheck = groupName.length in 1..30) }
        }
    }
}