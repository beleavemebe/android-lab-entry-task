package com.beleavemebe.androidlabentrytask.utils

import android.util.Log
import android.util.Patterns

object LoginArgsValidator {
    private const val TAG = "AuthVerifier"
    private const val MIN_PASSWORD_LENGTH = 6

    fun isEmailValid(email: String?) : Boolean {
        val result = Patterns.EMAIL_ADDRESS.matcher(email ?: "").matches()
        Log.d(TAG, "Email $email is valid = $result")
        return result
    }

    fun isPasswordValid(password: String?): Boolean {
        if (password == null || password.length < MIN_PASSWORD_LENGTH) return false

        var uppercaseLetterIsPresent = false
        var lowercaseLetterIsPresent = false
        var digitIsPresent = false

        password.forEach {
            val char: Char = Character.valueOf(it)
            when {
                char.isUpperCase() -> uppercaseLetterIsPresent = true
                char.isLowerCase() -> lowercaseLetterIsPresent = true
                char.isDigit() -> digitIsPresent = true
            }
        }

        val result = uppercaseLetterIsPresent && lowercaseLetterIsPresent && digitIsPresent
        Log.d(TAG, "Password $password is valid = $result")
        return result
    }
}