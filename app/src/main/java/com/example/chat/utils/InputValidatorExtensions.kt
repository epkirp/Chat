package com.example.chat.utils

import android.util.Patterns
import java.util.*

private const val PASSWORD_MIN_LENGTH = 6

fun areAllFieldsFilled(vararg fields: String): Boolean {
    return fields.all { !it.isBlank() }
}

fun isValidBirthday(birthday: Date?): Boolean {
    if (birthday == null) {
        return true
    }

    val startDate = getFormattedDateFromString("1.01.1900")
    val currentDate = Date()
    return birthday.before(currentDate) && birthday.after(startDate)
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= PASSWORD_MIN_LENGTH
}

fun isValidConfirmPassword(password: String?, confirmPassword: String?): Boolean {
    return password == confirmPassword
}