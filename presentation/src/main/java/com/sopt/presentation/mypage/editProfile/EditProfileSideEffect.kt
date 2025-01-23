package com.sopt.presentation.mypage.editProfile

sealed interface EditProfileSideEffect {
    data object NavigateUp : EditProfileSideEffect
    data object RequestImagePicker : EditProfileSideEffect
    data object ShowGalleryToast : EditProfileSideEffect
    data object NavigateToMyPage : EditProfileSideEffect
}
