package com.example.chat.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String?): String? {
    val parseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
    val formatDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    if (date == null) return null

    return try {
        val parsedDate: Date? = parseDateFormat.parse(date)
        formatDateFormat.format(parsedDate) ?: null
    } catch (e: Exception) {
        null
    }
}

fun getFormattedDateToString(date: Date?): String {
    //val parseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
    val parseDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val startDate = "01.01.1900"

    if (date == null) return startDate

    return try {
        parseDateFormat.format(date)
    } catch (e: Exception) {
        startDate
    }
}

fun getFormattedDateFromString(date: String?): Date? {

    val parseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())

    if (date == null) return null

    return try {
        parseDateFormat.parse(date)
    } catch (e: Exception) {
        null
    }
}
