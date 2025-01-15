package com.sopt.core.type

import androidx.annotation.StringRes
import com.sopt.core.R

enum class DialogType(
    @StringRes val content: Int,
    @StringRes val dismissText: Int,
    @StringRes val confirmText: Int
) {
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
    )
}
