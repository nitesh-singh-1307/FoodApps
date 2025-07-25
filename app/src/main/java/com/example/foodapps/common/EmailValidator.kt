package com.example.foodapps.common

import java.util.regex.Pattern

class EmailValidator {

    private val emailPattern = Pattern.compile(EMAIL_REGEX)

    fun validateEmail(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }

    companion object {
        private const val EMAIL_REGEX = """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""
    }
}