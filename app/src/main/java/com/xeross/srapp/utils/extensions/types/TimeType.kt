package com.xeross.srapp.utils.extensions.types

enum class TimeType(val format: String) {
    HOUR_MINUTE_SECOND_MILLISECONDS("HH:mm:ss.SSS"),
    MINUTE_SECOND_MILLISECONDS("mm:ss.SSS"),
    HOUR_MINUTE_SECOND("HH:mm:ss"),
    MINUTE_SECOND("mm:ss"),
}