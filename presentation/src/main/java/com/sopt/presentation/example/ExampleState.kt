package com.sopt.presentation.example

import com.sopt.core.state.UiState
import com.sopt.domain.entity.ExampleEntity

data class ExampleState(
    var followers: UiState<List<ExampleEntity>> = UiState.Empty,
)