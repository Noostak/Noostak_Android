package com.sopt.core.type

enum class CategoryType {
    IMPORTANT, SCHEDULE, HOBBY, ETC;

    companion object {
        fun fromText(text: String): CategoryType {
            return when (text.uppercase()) {
                "중요" -> IMPORTANT
                "일정" -> SCHEDULE
                "취미" -> HOBBY
                "기타" -> ETC
                else -> ETC
            }
        }
    }
}
