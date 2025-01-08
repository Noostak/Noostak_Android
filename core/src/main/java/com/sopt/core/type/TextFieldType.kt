package com.sopt.core.type

import androidx.annotation.StringRes
import com.sopt.core.R

enum class TextFieldType(
    @StringRes val placeholder: Int
) {
    SIGNUP(
        placeholder = R.string.tf_sign_up_placeholder
    ),
    GROUP(
        placeholder = R.string.tf_group_create_placeholder
    ),
    CALENDAR(
        placeholder = R.string.tf_calendar_info_placeholder
    )
}
