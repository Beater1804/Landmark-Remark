package com.beater.landmarkremark.utils.extensions

import com.beater.landmarkremark.utils.PASSWORD_LENGTH
import java.util.regex.Pattern

fun String.isCorrectEmailFormat(): Boolean {
    return Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(this).matches()
}

fun String.isCorrectPasswordFormat(): Boolean {
    return this.length >= PASSWORD_LENGTH
}