package com.sopt.core.type

import android.content.Context
import com.sopt.core.R

enum class CategoryType {
    IMPORTANT, SCHEDULE, HOBBY, ETC;

    companion object {
        fun fromText(context: Context, text: String): CategoryType {
            return when (text.uppercase()) {
                context.getString(R.string.category_chip_important) -> IMPORTANT
                context.getString(R.string.category_chip_schedule) -> SCHEDULE
                context.getString(R.string.category_chip_hobby) -> HOBBY
                context.getString(R.string.category_chip_etc) -> ETC
                else -> ETC
            }
        }
    }
}
