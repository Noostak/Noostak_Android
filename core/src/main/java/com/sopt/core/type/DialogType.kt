package com.sopt.core.type

import androidx.annotation.StringRes
import com.sopt.core.R

enum class DialogType(
    @StringRes val content: Int,
    @StringRes val dismissText: Int,
    @StringRes val confirmText: Int
) {
    LOGIN_KAKAO(
        content = R.string.text_dialog_type_login_kakao_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry
    ),
    LOGIN_GOOGLE(
        content = R.string.text_dialog_type_login_google_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry
    ),
    GROUP(
        content = R.string.text_dialog_type_group_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry
    ),
    LOGOUT(
        content = R.string.text_dialog_type_logout_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_logout
    ),
    WITHDRAWAL(
        content = R.string.text_dialog_type_withdrawal_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_withdrawal
    ),
    APPOINTMENT(
        content = R.string.text_dialog_type_appointment_content,
        dismissText = R.string.text_dialog_type_appointment_dismiss,
        confirmText = R.string.text_dialog_type_appointment_confirm
    )
}
