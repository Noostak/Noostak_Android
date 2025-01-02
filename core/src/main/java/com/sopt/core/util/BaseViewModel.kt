package com.sopt.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    private val _sideEffects = MutableSharedFlow<T>()
    val sideEffects: SharedFlow<T> get() = _sideEffects.asSharedFlow()

    protected fun emitSideEffect(sideEffect: T) {
        viewModelScope.launch {
            _sideEffects.emit(sideEffect)
        }
    }
}
