package com.sopt.presentation.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.domain.usecase.GetExampleUseCase
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val getExampleUseCase: GetExampleUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ExampleState> = MutableStateFlow(ExampleState())
    val state: StateFlow<ExampleState> get() = _state.asStateFlow()

    private val _sideEffects: MutableSharedFlow<ExampleSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<ExampleSideEffect> get() = _sideEffects.asSharedFlow()

    fun getFollowers(page: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(followers = UiState.Loading)
            getExampleUseCase(page).fold(
                onSuccess = {
                    _state.value = _state.value.copy(followers = UiState.Success(it))
                    _sideEffects.emit(ExampleSideEffect.ShowToast(R.string.server_success))
                },
                onFailure = {
                    _state.value =
                        _state.value.copy(followers = UiState.Failure(it.message.toString()))
                    _sideEffects.emit(ExampleSideEffect.ShowToast(R.string.server_failure))
                }
            )
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(ExampleSideEffect.NavigateUp)
        }
    }
}