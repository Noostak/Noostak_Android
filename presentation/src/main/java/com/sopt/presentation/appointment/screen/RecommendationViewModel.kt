package com.sopt.presentation.appointment.screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor() : ViewModel() {
    private val _likeStates = mutableStateMapOf<Long, Pair<Boolean, Int>>()
    val likeStates: Map<Long, Pair<Boolean, Int>> get() = _likeStates

    fun toggleLike(itemId: Long, defaultLiked: Boolean, defaultLikes: Int) {
        val (isLiked, likes) = _likeStates[itemId] ?: (defaultLiked to defaultLikes)
        _likeStates[itemId] = !isLiked to if (isLiked) likes - 1 else likes + 1
    }
}
