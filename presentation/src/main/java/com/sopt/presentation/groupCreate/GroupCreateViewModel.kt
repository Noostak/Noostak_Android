package com.sopt.presentation.groupCreate

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.GroupProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreateViewModel @Inject constructor() : BaseViewModel<GroupCreateSideEffect>() {
    private val _groupProfileState = MutableStateFlow(GroupProfileEntity())
    val groupProfileState: StateFlow<GroupProfileEntity> = _groupProfileState

    fun updateGalleryPermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            _groupProfileState.update { it.copy(isPermissionGranted = isGranted) }
        }
    }

    fun navigateToGroupCreateSuccess() {
        emitSideEffect(GroupCreateSideEffect.NavigateToGroupCreateSuccess)
    }

    fun requestGalleryPicker() {
        if (_groupProfileState.value.isPermissionGranted) {
            emitSideEffect(GroupCreateSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(GroupCreateSideEffect.ShowPermissionDeniedDialog)
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
