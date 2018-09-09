package com.memtrip.eosreach.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Date

fun LocalDateTime.fullDate(): String = DateTimeFormatter.ofPattern(
    "EEE dd MMM"
).format(this)

fun Date.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.UTC)
}