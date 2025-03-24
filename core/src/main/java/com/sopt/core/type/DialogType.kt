package com.sopt.core.type

import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R

enum class DialogType(
    @StringRes val content: Int,
    @StringRes val dismissText: Int,
    @StringRes val confirmText: Int,
    val paddingTop: Dp,
    val paddingBottom: Dp
) {
    LOGOUT(
        content = R.string.text_dialog_type_logout_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_logout,
        paddingTop = 41.dp,
        paddingBottom = 36.dp
    ),
    WITHDRAWAL(
        content = R.string.text_dialog_type_withdrawal_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_withdrawal,
        paddingTop = 30.dp,
        paddingBottom = 26.dp
    ),
    APPOINTMENT(
        content = R.string.text_dialog_type_appointment_content,
        dismissText = R.string.text_dialog_type_appointment_dismiss,
        confirmText = R.string.text_dialog_type_appointment_confirm,
        paddingTop = 30.dp,
        paddingBottom = 26.dp
    ),
    DATA_FAILURE(
        content = R.string.text_dialog_type_data_failure_content,
        dismissText = R.string.text_dialog_type_data_failure_dismiss,
        confirmText = R.string.text_dialog_type_data_failure_confirm,
        paddingTop = 30.dp,
        paddingBottom = 26.dp
    ),
    NETWORK_FAILURE(
        content = R.string.text_dialog_type_network_failure_content,
        dismissText = R.string.text_dialog_type_network_failure_dismiss,
        confirmText = R.string.text_dialog_type_network_failure_confirm,
        paddingTop = 30.dp,
        paddingBottom = 26.dp
    ),
    NETWORK_LOGIN_KAKAO_FAILURE(
        content = R.string.text_dialog_type_login_kakao_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry,
        paddingTop = 24.dp,
        paddingBottom = 20.dp
    ),
    NETWORK_LOGIN_GOOGLE_FAILURE(
        content = R.string.text_dialog_type_login_google_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry,
        paddingTop = 24.dp,
        paddingBottom = 20.dp
    ),
    NETWORK_GROUP_CREATE_FAILURE(
        content = R.string.text_dialog_type_group_content,
        dismissText = R.string.text_dialog_type_dismiss_cancel,
        confirmText = R.string.text_dialog_type_confirm_retry,
        paddingTop = 24.dp,
        paddingBottom = 20.dp
    )
}
