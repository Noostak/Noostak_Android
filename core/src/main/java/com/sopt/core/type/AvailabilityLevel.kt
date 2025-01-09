package com.sopt.core.type

enum class AvailabilityLevel(val range: IntRange) {
    NONE(0..0),
    FEW(1..20),
    SOME(21..40),
    MANY(41..60),
    MOST(61..80)
}
